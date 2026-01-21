package com.goldyonwar.bidoneassessment.ui.screens.list

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.goldyonwar.bidoneassessment.domain.model.User
import com.goldyonwar.bidoneassessment.ui.theme.BidOneAssessmentTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserListScreen(
    viewModel: UserListViewModel = hiltViewModel(),
    onUserClick: (User) -> Unit
) {

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("BidOne Users") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            contentAlignment = Alignment.Center
        ) {
            when (val state = uiState) {
                is UserListUiState.Loading -> {
                    CircularProgressIndicator()
                }

                is UserListUiState.Error -> {
                    ErrorContent(
                        message = state.message,
                        onRetry = { viewModel.fetchUsers() }
                    )
                }

                is UserListUiState.Success -> {
                    UserListContent(users = state.users, onUserClick = onUserClick)
                }
            }
        }
    }
}

@Composable
fun ErrorContent(
    message: String,
    onRetry: () -> Unit
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = message, color = MaterialTheme.colorScheme.error)
        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = onRetry) {
            Text("Retry")
        }
    }
}

@Composable
fun UserListContent(
    users: List<User>,
    onUserClick: (User) -> Unit
) {
    LazyColumn(
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(
            items = users,
            key = { user -> user.id }
        ) { user ->
            UserItem(user = user, onClick = { onUserClick(user) })
        }
    }
}

@Composable
fun UserItem(user: User, onClick: () -> Unit) {

    val imageUrl = "https://ui-avatars.com/api/?name=${user.name}"
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(imageUrl)
                    .crossfade(true)
                    .build(),
                contentDescription = "Avatar for ${user.name}",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(50.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.surfaceVariant)
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column {
                Text(
                    text = user.name,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = user.email,
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Gray
                )
            }
        }
    }
}


val fakeUser1 = User(
    id = 1,
    name = "Leanne Graham",
    email = "sincere@april.biz",
    phone = "1-770-736-8031 x56442",
    website = "hildegard.org",
    companyName = "Romaguera-Crona"
)

val fakeUser2 = User(
    id = 2,
    name = "Ervin Howell",
    email = "shanna@melissa.tv",
    phone = "010-692-6593 x09125",
    website = "anastasia.net",
    companyName = "Deckow-Crist"
)

val fakeUserList = listOf(fakeUser1, fakeUser2, fakeUser1, fakeUser2)

@Preview(showBackground = true)
@Composable
fun UserItemPreview() {
    BidOneAssessmentTheme {
        UserItem(user = fakeUser1, onClick = {})
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFF0F0F0)
@Composable
fun UserListContentPreview() {
    BidOneAssessmentTheme {
        UserListContent(users = fakeUserList, onUserClick = {})
    }
}

@Preview(showBackground = true)
@Composable
fun LoadingPreview() {
    BidOneAssessmentTheme {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ErrorPreview() {
    BidOneAssessmentTheme {
        ErrorContent("Failed to load users.", onRetry = {})
    }
}