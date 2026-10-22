# MPAZ Pañol - Sistema Móvil de Inventario, Reserva y Préstamo

![Android Studio](https://img.shields.io/badge/Android%20Studio-2023.1+-green.svg)
![Kotlin](https://img.shields.io/badge/Kotlin-1.9+-blue.svg)
![Material 3](https://img.shields.io/badge/UI-Material%20Design%203-purple.svg)
![Architecture](https://img.shields.io/badge/Architecture-MVVM-orange.svg)

**MPAZ Pañol** es un Producto Mínimo Viable (MVP) móvil diseñado para la **Escuela Escritora Marcela Paz (San Bernardo)** en el marco de la asignatura **DSY1105 - Desarrollo de Aplicaciones Móviles** (Duoc UC / CITT). 

El sistema reemplaza las solicitudes informales vía correo electrónico por una plataforma centralizada que administra el catálogo de equipamiento tecnológico (proyectores, kits de robótica, notebooks, equipamiento de red), optimizando la logística de reservas y control de trazabilidad.

---

## 🚀 Características Principales

* **Consulta de Catálogo Interactivo:** Filtrado por categorías (Proyectores, Robótica, Notebooks, Alargadores) y actualización de stock disponible en tiempo real.
* **Carrito de Solicitudes Internas:** Permite agrupar múltiples recursos en una sola solicitud consolidada antes de enviar el pedido.
* **Validación Crítica de Anticipación (3 Horas):** Bloqueo automático mediante código de solicitudes efectuadas con menos de 3 horas respecto al horario de uso programado.
* **Recursos Nativos:** Integración de `DatePickerDialog` de Material 3 para la selección nativa e intuitiva de fechas.

---

## 🛠️ Stack Tecnológico

* **Lenguaje:** Kotlin
* **IDE:** Android Studio
* **UI Framework:** Jetpack Compose con **Material Design 3**
* **Patrón Arquitectónico:** MVVM (Model-View-ViewModel) con `StateFlow`
* **Persistencia Local:** Room (SQLite)
* **Red:** Retrofit + REST API (Spring Boot simulado)
* **Navegación:** Jetpack Navigation Compose

---

## 📁 Estructura del Proyecto

```text
com.example.panol
├── data/
│   ├── database/    # Configuración de Room (AppDatabase)
│   └── local/       # Entities y DAOs (UsuarioDao, EquipoEntity, etc.)
├── model/           # Modelos de datos de la aplicación
├── ui/
│   ├── components/  # Header, Footer, Cards reutilizables
│   ├── navigation/  # AppNavigation y gestión de rutas
│   ├── screen/      # Pantallas UI (Login, Catalogo, Reserva, Carrito, etc.)
│   └── theme/       # Estilos, Colores y Tipografía (Material3)
└── viewmodel/       # Lógica de negocio y manejo de estados UI
```

---

## 📋 Requisitos para Ejecutar el Proyecto

1. **Android Studio:** Jellyfish | 2023.3.1 o superior.
2. **JDK:** Java 17 o posterior.
3. **SDK de Android:** Mínimo API 26 (Android 8.0 Oreo), Target API 34.
4. **Dispositivo/Emulador:** Teléfono inteligente bajo sistema operativo Android con conectividad a red WiFi o datos.

---

## ⚙️ Instalación y Configuración

1. **Clonar el repositorio:**
   ```bash
   git clone https://github.com/tu-usuario/mpaz-panol.git
   ```
2. **Abrir en Android Studio:**
   * Selecciona `File > Open...` y busca la carpeta del proyecto.
3. **Sincronizar Gradle:**
   * Deja que Gradle descargue todas las dependencias (`Sync Project with Gradle Files`).
4. **Ejecutar la App:**
   * Selecciona un emulador (p. ej., Pixel 6 API 34) o conecta un dispositivo físico mediante Depuración USB.
   * Haz clic en **Run ('app')** o presiona `Shift + F10`.

---

## 🔐 Protección de Datos Personales (Ley N.º 19.628 y N.º 21.719)

Este proyecto es un prototipo académico MVP. **Toda información, catálogo, usuarios y registros de correo presentados en la aplicación son estrictamente sintéticos y ficticios**, creados con el único propósito de evaluación académica sin involucrar datos reales de la institución escolar.

---
Sebastian Herrera
