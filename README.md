# Demo projet Unlck (pour Leboncoin)

L'application est un multi-module projet utilisant la Clean Architecture avec MVVM pour la couche presentation.

# Module Data : 
Contient la logique gouvernant les sources des données. Deux sources sont utilisé :
1. Réseau : permet la récuperation des données de https://static.leboncoin.fr/img/shared/technical-test.json
2. Base de données SQLite permettant de cacher les donées récuperées depuis les réseau et les proposer quand on n'a pas besoin de faire une requete réseau.

# Module Domain : 
Permet de faire la liason entre la couche presentation et data. D'une part il permet de commander les données depuis la couche data et les préparer pour la couche presentation, et d'une autre part transmettre les actions de l'utilisateur vers la couche data.

# Librairies utilisées : 
1. Hilt-Dagger pour l'injection des dependances, permettant de ne pas se soucier de l'instantiation de classes. Hilt est choisit pour la facilité de son integration avec les applications Android, et la facilité de tests.
2. Retrofit pour effectuer les requetes Http facilement.
3. Room pour la sauvegarde de data en local. Facilite l'utilisation de SQLite.
3. Coroutines Flows qui permet un streamin de data sans intereption, comme il permet le threading facilement.
4. Navigation pour la navigation entre les fragments.
5. Picasso pour le chargement et le caching des images.
6. Palette purment pour le UI permet de trouver les couleurs utilisées par exemple dans une image.

# La stratégie de fetch and cache : 
Un controlleur est utilisé pour controller quand forcer un fetch des données depuis l'api. La stratégie est comme suit : 
- Si ouverture de l'application, un fetch est excuté, le cache (si existe) est utilisé pour afficher les données cashées en attendant l'aboutissement de la requête.
- Le fetch est forcé si un temps prédifini est passé depuis le précedant fetch effectué.
- L'utilisateur peut forcer un fetch si pull to refresh.

# Tests unitaires et d'intégration :
Un minimum de tests écris pour surtout tester :
* La stratégie du caching.
* La navigation
* Le viewModel responsable d'afficher la listes des albums.
