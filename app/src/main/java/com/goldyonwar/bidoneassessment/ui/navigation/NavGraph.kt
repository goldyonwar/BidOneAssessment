package com.goldyonwar.bidoneassessment.ui.navigation

import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.goldyonwar.bidoneassessment.ui.screens.detail.UserDetailScreen
import com.goldyonwar.bidoneassessment.ui.screens.list.UserListScreen

@Composable
fun AppNavHost() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "user_list") {

        composable("user_list") {
            UserListScreen(
                onUserClick = { user ->
                    val encodedName = Uri.encode(user.name)
                    val encodedEmail = Uri.encode(user.email)
                    val encodedPhone = Uri.encode(user.phone)
                    val encodedWebsite = Uri.encode(user.website)
                    val encodedCompany = Uri.encode(user.companyName)

                    navController.navigate(
                        "user_detail/$encodedName/$encodedEmail/$encodedPhone/$encodedWebsite/$encodedCompany"
                    )
                }
            )
        }

        composable(
            route = "user_detail/{name}/{email}/{phone}/{website}/{company}",
            arguments = listOf(
                navArgument("name") { type = NavType.StringType },
                navArgument("email") { type = NavType.StringType },
                navArgument("phone") { type = NavType.StringType },
                navArgument("website") { type = NavType.StringType },
                navArgument("company") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val name = backStackEntry.arguments?.getString("name") ?: ""
            val email = backStackEntry.arguments?.getString("email") ?: ""
            val phone = backStackEntry.arguments?.getString("phone") ?: ""
            val website = backStackEntry.arguments?.getString("website") ?: ""
            val company = backStackEntry.arguments?.getString("company") ?: ""

            UserDetailScreen(
                name = name,
                email = email,
                phone = phone,
                website = website,
                company = company,
                onBackClick = { navController.popBackStack() }
            )
        }
    }
}