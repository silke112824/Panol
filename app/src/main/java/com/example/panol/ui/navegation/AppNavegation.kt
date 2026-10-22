package com.example.panol.ui.navigation

import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.myappnavigation.ui.screen.Principal
import com.example.panol.ui.screen.CatalogoScreen
import com.example.panol.ui.screen.Login
import com.example.panol.ui.screen.Register
import com.example.panol.ui.screen.ScreenInicio


object Routes{
    const val INICIO = "inicio"
    const val LOGIN = "login"
    const val REGISTER = "register"
    const val PRINCIPAL = "principal"
    const val CATALOGO = "catalogo"
}

@Composable
fun AppNavigation(){
    val navController = rememberNavController()
    NavHost(
        navController=navController,
        startDestination = Routes.INICIO,     //definimos el inicio
        //animaciones
        enterTransition = { slideInHorizontally(initialOffsetX = { it }) + fadeIn() },
        exitTransition = { slideOutHorizontally(targetOffsetX = { -it }) + fadeOut() },
        popEnterTransition = { slideInHorizontally(initialOffsetX = { -it }) + fadeIn() },
        popExitTransition = { slideOutHorizontally(targetOffsetX = { it }) + fadeOut() }

    ){
        composable(Routes.INICIO) {
            ScreenInicio (
                onNavigateToLogin = {
                    navController.navigate(Routes.LOGIN)
                }
            )
        }//composable inicial

        composable(Routes.LOGIN) {
            Login(
                onNavigateToRegister = {
                    navController.navigate(Routes.REGISTER)
                },
                onLoginSuccess = {
                    //navega a la pantalla inicial
                    navController.navigate(Routes.PRINCIPAL) {
                        popUpTo(Routes.LOGIN) { inclusive = true }
                    }
                }
            )
        }

        composable (Routes.REGISTER){
            Register(
                onNavigateToLogin = {
                    navController.navigate(Routes.LOGIN) {
                        // limpia la pila para no acumular pantallas login activas
                        popUpTo(Routes.LOGIN) { inclusive = true }
                    }
                }
            )
        }

        composable(Routes.PRINCIPAL) {
            Principal (
                onNavigateToCatalogo = {
                    navController.navigate(Routes.CATALOGO)
                },
                onNavigateToReserva = {

                }
            )
        }

        composable(Routes.CATALOGO) {
            CatalogoScreen (
                onNavigateToReserva = {
                    //navController.navigate(Routes.RESERVA)
                }
            )
        }










    }
}