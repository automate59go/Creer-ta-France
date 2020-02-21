# Cree ta FRANCE

## Auteurs

- D Deroubaix François
- A Sannier Alban

## Démarrage 

- Java 8 ou supérieur
   
## Démarrage 

- Exécutez compile.sh puis run.sh

## Description

Description originale du projet : voir le fichier pdf présent à la racine

## Présentation

Ce jeu a une décomposition de tours en plusieurs parties et comporte des conditions de victoire simple et facilement lisibles dans l’interface de notre logiciel.

Le but de notre jeu est de répondre à des questions pour influer positivement 3 jauges, si l’une d’elle est complète, vous avez gagné ! Mais attention des événements ont une chance de se produire au début de chaque tour, ces événements influencent souvent négativement vos jauges et votre budget. Pour contrebalancer ces événements, vous pouvez créer des bâtiments, qui vous rapportera de l’argent à chaque tour. Certains d’entre eux peuvent influencer positivement sur vos jauges. Cependant trop dépenser peut vous mener a votre perte puisque si votre budget est en dessous de -2500 vous perdez.

La décomposition est la suivante, d’abord est affichée l’interface de votre pays,
puis si un événement se produit, il est affiché,
Il vous est proposé de répondre à 3 types de questions différentes,
Après avoir fait votre choix, la question est affichée, puis vous pouvez répondre.

Les questions sont stockées dans trois csv, l’ordre des réponses proposées sera aléatoire, une question posée ne sera pas posée une deuxième fois, si il n’y a plus de questions disponibles dans le csv demandé , une question d’un autre csv est posée.

## Comment jouer ?

Le gameplay de ce jeu est très simple il suffit d'entrer un chiffre enfonction de l'action demandé

Exemple :

```
   Voulez-vous jouer ? 
   				1. Oui je veux commencer une partie
   				2. Je veux lire les règles
   				3. Je veux quitter
   Choix :
```

Il suffit ici de tapper 1 pour jouer, 2 pour lire les règles et 3 pour quitter la partie.

###Maintenant que le jeu est présenté à vous de jouez !
