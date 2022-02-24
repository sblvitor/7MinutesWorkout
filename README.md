# 7 Minutes Workout App

- Um aplicativo de exercícios físicos que pode ser feito em qualquer lugar, casa, praça entre outros lugares. Tradução literal: 7 Minutos de Treino

- A aplicação faz o uso de alguns recursos e abordagens novas em relação aos outros apps (QuizApp e DrawingApp).

- Entre eles está o uso de **Viewbinding**, abordagem recomendada pelo Google;

- O uso de uma toolbar customizada;
- Timers;
- Uma progress bar customizada para rodar no sentido anti-horário relacionada com o timer;

- Faz o uso de **TextToSpeech** que fala o nome do exercício, e também adiciona um **Media Player** que indica quando tal exercício chegou ao fim e entra no tempo de descanso;

- Usa **Recyclerview** juntamente com viewbinding e nesse caso, serve para mostrar a quantidade de exercícios, e em qual exercício está atualmente. Com a recylerview é possível
  mudar dinâmicamente a quantidade de exercícios sem precisar alterar manualmente a UI. Foram criados os próprios customs **adapter** e **viewholder** para essa recyclerview;

- Para a activity da calculadora BMI, foi utilizado um **Radio Group** com dois radio buttons, para escolher entre o sistema métrico ou o sistema dos EUA.

- A ultima activity "History", é um histórico de treinos completos, que mostra a data em que esse treino foi completado. Essa tela faz o uso de Room, que faz parte da biblioteca Jetpack,
  sendo o modo mais prático de armazenar dados permanentemente localmente. Uma outra aplicação foi desenvolvida para estudo antes de aplicar os conceitos nesta, e se encontra neste perfil
  como "Room-Demo". Essa activity também utiliza Recycler View com o adapter e viewholder customizados.
