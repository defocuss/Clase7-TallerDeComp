# Detección de Movimiento con Sensores

## Objetivo

Desarrollar una aplicación para Android (APK) que utilice el sensor de acelerómetro y el giroscopio para detectar si el dispositivo ha sido colocado en una superficie plana o si está siendo agitado. La aplicación debe emitir un sonido cuando el dispositivo está en movimiento.

## Requisitos Funcionales

1. La aplicación debe iniciar con una interfaz sencilla que indique dos estados: **"Estable"** y **"En Movimiento"**.
2. Utilizando el **sensor de acelerómetro**, la aplicación debe ser capaz de detectar si el dispositivo está en una **superficie plana**. Si el dispositivo está estable, se reproducirá un **sonido especial** para este estado.
3. Utilizando el **sensor de giroscopio**, la aplicación debe ser capaz de detectar si el dispositivo está siendo **agitado o rotado**. Si el dispositivo está en movimiento, se reproducirá un **sonido especial diferente**.
4. La aplicación debe mostrar el estado actual del dispositivo en la pantalla (ya sea **"Estable"** o **"En Movimiento"**) basándose en los datos de los sensores.
5. Debe haber una **opción para reiniciar la detección** y volver a evaluar el estado del dispositivo.

