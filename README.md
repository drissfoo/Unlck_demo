# Demo projet Unlck (pour Leboncoin)

L'application est un projet multi-module utilisant la Clean Architecture avec MVVM pour la couche présentation.

## Module Data : 
Contient la logique gouvernant les sources des données. Deux sources sont utilisé :
1. Réseau : permet la récuperation des données de https://static.leboncoin.fr/img/shared/technical-test.json
2. Base de données SQLite permettant de cacher les données récupérées depuis les réseau et les proposer quand on n'a pas besoin de faire une requête réseau.

## Module Domain : 
Permet de faire la liaison entre la couche présentation et data. D'une part il permet de commander les données depuis la couche data et les préparer pour la couche présentation, et d'une autre part transmettre les actions de l'utilisateur vers la couche data.

## Librairies utilisées : 
1. Hilt-Dagger pour l'injection des dépendances, permettant de ne pas se soucier de l'instanciation de classes. Hilt est choisi pour la facilité de son intégration avec les applications Android, et la facilité de tests.
2. Retrofit pour effectuer les requêtes Http facilement.
3. Room pour la sauvegarde de data en local. Facilite l'utilisation de SQLite.
3. Coroutines Flows qui permet un streaming de data sans interruption, comme il permet le threading facilement.
4. Navigation pour la navigation entre les fragments.
5. Picasso pour le chargement et le caching des images.
6. Palette purement pour le UI permet de trouver les couleurs utilisées par exemple dans une image.

## La stratégie de fetch and cache : 
Un controller est utilisé pour controller quand forcer un fetch des données depuis l'api. La stratégie est comme suit : 
- Si ouverture de l'application, un fetch est exécuté, le cache (si existe) est utilisé pour afficher les données cachées en attendant l'aboutissement de la requête.
- Le fetch est forcé si un temps prédéfini est passé depuis le précédent fetch effectué.
- L'utilisateur peut forcer un fetch si pull to refresh.

## Changement de configuration : 
Les changements de configuration sont gérés par les LiveData et le SavedStateHandle.

## Tests unitaires et d'intégration :
Un minimum de tests écrits pour surtout tester :
* La stratégie du caching.
* La navigation
* Le viewModel responsable d'afficher la liste des albums.
