package com.example.panol.ui.navigation

import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.panol.ui.screen.Principal
import com.example.panol.ui.screen.CarritoScreen
import com.example.panol.ui.screen.CatalogoScreen
import com.example.panol.ui.screen.Login
import com.example.panol.ui.screen.Register
import com.example.panol.ui.screen.ReservaScreen
import com.example.panol.ui.screen.ScreenInicio
import com.example.panol.ui.viewmodel.CatalogoViewModel


object Routes{
    const val INICIO = "inicio"
    const val LOGIN = "login"
    const val REGISTER = "register"
    const val PRINCIPAL = "principal"
    const val RESERVA = "reserva"
    const val CATALOGO = "catalogo"
    const val CARRITO = "carrito"
}

@Composable
fun AppNavigation(){
    val navController = rememberNavController()
    val compartidoCatalogoViewModel: CatalogoViewModel = viewModel ()
    val sharedCatalogoViewModel: CatalogoViewModel = viewModel()

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
                    }
                }
            )
        }

        composable (Routes.REGISTER){
            Register(
                onNavigateToLogin = {
                    navController.navigate(Routes.LOGIN) {
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
                    navController.navigate(Routes.RESERVA)
                }
            )
        }

        composable(Routes.CATALOGO) {
            CatalogoScreen (
                viewModel = sharedCatalogoViewModel,
                onNavigateToCarrito = {
                    navController.navigate(Routes.CARRITO)
                }
            )
        }

        composable (Routes.CARRITO){
            CarritoScreen (
                viewModel = compartidoCatalogoViewModel,
                onNavigateToReserva = {
                    navController.navigate(Routes.RESERVA)
                },
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }

        // Faltaba agregar la ruta de Reserva
        composable (Routes.RESERVA){
            ReservaScreen (
                onReservaExitosa = {
                    // Al terminar vuelve al principal limpiando el historial
                    navController.navigate(Routes.PRINCIPAL) {
                        popUpTo(Routes.PRINCIPAL) { inclusive = true }
                    }
                }
            )
        }
    }
}