# Recipez
Meal Planner - Recipe Binder - Grocery List Maker

Notes:  
    - We may need to add 3 classes Home, HomeModel, HomeViewModel for data persistence.
    - Data persistence.
        * Boolean value swap for recipe and grocery list save.
            RECIPE (been saved?)
            -false: dont request new list of recipes when opening recipe book
            -true: request new list of recipes in recipe book 
            GROCERYLIST (been saved?)
            -false: dont request new list of groceries
            -true: request data for up-to-date list of groceries
    - Observer Pattern (OP)
        * Week 4 CPSC 221. I think something in there will help us with 
        data persistence. The Home or one of the Home related classes will have 
        some logc similar to IdahoFishAndGame.java.
        * All, some of these or other classes would be observers watching for a change: 
            - CreateRecipeView
            - RecipeBookView
            - mealPlannerView
    - Data persistence issue may be solved with OP. Investigate OP solution and fall back 
    on Boolean if unable to create OP solution.
