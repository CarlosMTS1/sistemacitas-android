# 📅 SistemaCitas - Gestión de Actividades del Centro Integral

![Android](https://img.shields.io/badge/Android-3DDC84?style=for-the-badge&logo=android&logoColor=white)
![Kotlin](https://img.shields.io/badge/Kotlin-0095D5?style=for-the-badge&logo=kotlin&logoColor=white)
![Firebase](https://img.shields.io/badge/Firebase-FFCA28?style=for-the-badge&logo=firebase&logoColor=black)

Aplicación móvil Android para la gestión eficiente de actividades y citas programadas en centros integrales, facilitando la coordinación de servicios comunitarios.

## 📋 Tabla de Contenidos

- [Descripción](#-descripción)
- [Características](#-características)
- [Tecnologías](#-tecnologías)
- [Arquitectura](#-arquitectura)
- [Instalación](#-instalación)
- [Configuración](#-configuración)
- [Uso](#-uso)
- [Estructura del Proyecto](#-estructura-del-proyecto)
- [Roadmap](#-roadmap)
- [Equipo](#-equipo)
- [Licencia](#-licencia)

## 🎯 Descripción

**SistemaCitas** es una solución móvil desarrollada en Android que permite a centros integrales gestionar de manera centralizada todas sus actividades comunitarias, incluyendo capacitaciones, talleres, charlas, atenciones, operativos y prácticas profesionales.

### Problema que resuelve

Los centros integrales enfrentan desafíos en la coordinación de múltiples actividades simultáneas, generando:
- Conflictos de horarios
- Falta de visibilidad de la carga semanal
- Dificultad para notificar cambios
- Gestión manual de documentación

### Solución

Una aplicación móvil que centraliza la información, automatiza notificaciones y proporciona herramientas de gestión eficientes con acceso en tiempo real desde cualquier dispositivo Android.

## ✨ Características

### Funcionalidades Principales

- 🔐 **Autenticación Segura**
  - Login con usuario y contraseña
  - Recuperación de contraseña vía email
  - Registro de usuarios (solo administradores)

- 📆 **Calendario Interactivo**
  - Vista semanal de citas
  - Visualización por día y hora
  - Código de colores por tipo de actividad
  - Acceso rápido a detalles

- 📝 **Gestión de Actividades**
  - Crear actividades puntuales o periódicas
  - Modificar información de actividades
  - Cancelar con motivo
  - Reagendar con nueva fecha/hora
  - Adjuntar archivos y documentos

- 🔔 **Sistema de Notificaciones**
  - Alertas push personalizadas
  - Notificaciones de actividades próximas
  - Avisos de cambios y cancelaciones

- ⚙️ **Mantenedores (Admin)**
  - Tipos de actividad
  - Lugares
  - Oferentes
  - Socios comunitarios
  - Proyectos

- 👥 **Sistema de Roles**
  - **Administrador:** Acceso completo
  - **Usuario:** Visualización y consultas

## 🛠 Tecnologías

### Frontend
- **Lenguaje:** Kotlin
- **IDE:** Android Studio
- **Min SDK:** Android 7.0 (API 24)
- **Target SDK:** Android 14 (API 34)
- **UI:** Material Design 3

### Backend
- **Firebase Authentication** - Autenticación de usuarios
- **Firebase Firestore** - Base de datos NoSQL
- **Firebase Cloud Messaging** - Notificaciones push
- **Firebase Storage** - Almacenamiento de archivos

### Arquitectura y Patrones
- **MVVM** (Model-View-ViewModel)
- **Repository Pattern**
- **Dependency Injection** (si aplica)

### Librerías Principales
```gradle
// Firebase
implementation 'com.google.firebase:firebase-auth-ktx'
implementation 'com.google.firebase:firebase-firestore-ktx'
implementation 'com.google.firebase:firebase-messaging-ktx'
implementation 'com.google.firebase:firebase-storage-ktx'

// Android Jetpack
implementation 'androidx.lifecycle:lifecycle-viewmodel-ktx'
implementation 'androidx.navigation:navigation-fragment-ktx'
implementation 'androidx.recyclerview:recyclerview'

// Material Design
implementation 'com.google.android.material:material'

// Coroutines
implementation 'org.jetbrains.kotlinx:kotlinx-coroutines-android'
```

## 🏗 Arquitectura

### Patrón MVVM

```
┌─────────────┐
│     View    │ (Activity/Fragment)
└──────┬──────┘
       │ observa
       ▼
┌─────────────┐
│  ViewModel  │ (Lógica de presentación)
└──────┬──────┘
       │ usa
       ▼
┌─────────────┐
│ Repository  │ (Capa de datos)
└──────┬──────┘
       │ accede
       ▼
┌─────────────┐
│   Firebase  │ (Fuente de datos)
└─────────────┘
```

### Capas de la Aplicación

1. **Capa de Presentación (UI)**
   - Activities y Fragments
   - Layouts XML
   - Adapters

2. **Capa de Lógica (ViewModel)**
   - ViewModels por funcionalidad
   - LiveData para observación
   - Manejo de estados

3. **Capa de Datos (Repository)**
   - Repositorios por entidad
   - Abstracción de Firebase
   - Caché local (si aplica)

4. **Capa de Servicios (Firebase)**
   - Servicios de autenticación
   - Operaciones de base de datos
   - Gestión de notificaciones

## 📦 Instalación

### Prerrequisitos

- Android Studio Hedgehog o superior
- JDK 17+
- Cuenta de Firebase (proyecto configurado)
- Dispositivo Android con API 24+ o emulador

### Pasos de Instalación

1. **Clonar el repositorio**
```bash
git clone https://github.com/tuusuario/sistemacitas.git
cd sistemacitas
```

2. **Abrir en Android Studio**
```
File > Open > Seleccionar carpeta del proyecto
```

3. **Configurar Firebase**
   - Descargar `google-services.json` desde Firebase Console
   - Colocar en `app/` directorio
   - Verificar que esté en `.gitignore`

4. **Sincronizar Gradle**
```
File > Sync Project with Gradle Files
```

5. **Ejecutar la aplicación**
```
Run > Run 'app'
```

## ⚙️ Configuración

### Firebase Setup

1. Crear proyecto en [Firebase Console](https://console.firebase.google.com)
2. Añadir aplicación Android
3. Habilitar servicios:
   - Authentication (Email/Password)
   - Firestore Database
   - Cloud Messaging
   - Storage

### Variables de Configuración

Las credenciales de Firebase se manejan a través de `google-services.json`. **Nunca subir este archivo al repositorio público.**

### Configuración de Permisos (AndroidManifest.xml)

```xml
<uses-permission android:name="android.permission.INTERNET" />
<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
<uses-permission android:name="android.permission.POST_NOTIFICATIONS" />
```

## 🚀 Uso

### Para Usuarios

1. **Iniciar Sesión**
   - Ingresar email y contraseña
   - Recuperar contraseña si es necesario

2. **Ver Calendario**
   - Navegar entre semanas
   - Tocar una cita para ver detalles

3. **Consultar Actividades**
   - Ver lista completa
   - Filtrar por tipo o fecha

### Para Administradores

Además de lo anterior:

4. **Crear Actividad**
   - Completar formulario
   - Seleccionar periodicidad
   - Asignar recursos

5. **Gestionar Mantenedores**
   - Crear/editar tipos de actividad
   - Administrar lugares y oferentes

6. **Registrar Usuarios**
   - Crear nuevos usuarios
   - Asignar roles

## 📁 Estructura del Proyecto

```
app/
├── src/
│
