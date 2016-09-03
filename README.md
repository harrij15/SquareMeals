# SquareMeals
**This is the end user documentation.** The guide to acquire the development environment is provided below though.
If you wish to observe the Technical User documentation check [here](https://github.com/harrij15/SquareMeals/blob/master/developers_documentation.md).


SquareMeals is an mobile application that allows users (mainly college students) to compile different easy-to-make and affordable recipes that suit their dietary needs into a cookbook. This app also seeks to create a community where college students on the same campus can share/suggest recipes to others.

##Table of Contents
- [Introduction](#squaremeals)
- [What It Does] (#what-it-does)
- [Installation](#installation)
- [Using the Interface](#using-the-interface)
  - [Account Creation] (#account-creation)
  - [Preference Customization] (#preference-customization)
  - [Searching] (#searching)
- [Known Issues](#known-issues)
- [Blog](#blog)
- [License](#license)

## What It Does
Currently the app is able to filter recipe searches based on dietary preferences through an external API, the Yummly API. We ultimately want to move away from the Yummly API and be able to have our own recipe database. Some other features we want to implement in the future:
- Create a website
- Expand to iOS
- More dietary preferences available for users
- Ability for to input ingredients and get recipes
- "Cookbook" for the users' personal favorites
- Also search restaurants in the area
- Have a "college community" for users
- Provide grocery prices from nearby stores

## Installation
Thus far, the app is not complete not up on the app store, thus the only way to acquire the app is mostly for developmental purposes.
If you wish to utilize our app, you must access it through the Android Studio emulator or a similar environment.
Please refer to our [Technical User](https://github.com/harrij15/SquareMeals/blob/master/developers_documentation.md) documentation for instructions on how to use these.

## Using the Interface
The Android Studio Emulator appears as such. Soemtimes the app opens automatically but sometimes you must look for it in your apps.
To do so, refer to the image below. Select the boxed, red icon and scroll until you find "SquareMeals"

![Android Home Page](http://orig09.deviantart.net/25d1/f/2016/125/4/6/1_by_miinji-da1gpqs.png)

### Account Creation
After being greeted by the SquareMeals logo, you are met with the login screen. As seen below:

![Login Screen](http://orig15.deviantart.net/df4c/f/2016/125/5/1/2_by_miinji-da1gpqp.png)

If you are a new user, click on the [Sign Up] button. You will be met with a standard login screen, which prompts you for your *name*
, a *username*, your *email* and a *password*.

You must have a unique username.

Password must be between 8 and 16 characters.

### Preference Customization
Upon the creation of your account, you will be met with the Preference Selection Page as shown below.

![Preferences Page](http://orig05.deviantart.net/e82e/f/2016/125/a/7/3_by_miinji-da1gskr.png)

After your preference is selected, your homepage will show recipes applicable to your current diet.

NOTE: You are only able to select a single type of diet as of now. We hope to expand the dietary selection in the future
as well as let people select many types of restrictions, primarily allergies foods and similar types.

**Editting Preferences**
If you want to change your diet select the dropdown menu shown below:

![Editting Preferences](http://orig14.deviantart.net/e450/f/2016/126/7/8/4_by_miinji-da1gwl2.png)

And select the [Edit Preferences] option. There you will be prompted to change your preferences once again.

### Searching

To search, click the magnifying glass icon, shown below:

![Search function](http://orig01.deviantart.net/195c/f/2016/126/1/2/5_by_miinji-da1gwky.png)

Search for a recipe you'd like and it filters hits according to your diet.

Selecting a recipe will display an *image* of the recipe, the *ingredients* required, the *time required* to make and a
*hyperlink* to the original recipe.

As of now, we do not have the procedure available on the recipes, so please refer to the hyperlink for now for the procedure.

## Known Issues
One of the main problems with our app so far is Lacto-Vegetarian will come up with cheese products.
We have suspicions that similar effects might be applied to Ovo-vegetarian as well.

## Blog

We've maintained this [blog](https://rcos.io/projects/harrij15/SquareMeals/blog) to provide updates in development.

## License

You can refer to the [LICENSE.md](https://github.com/harrij15/SquareMeals/blob/master/LICENSE.md) or read the terms below.

###BSD 3-Clause LICENSE

Copyright (c) 2016, Jon Harris, Shreya Patel, Zefanya Putri, Erin Jordan, Helen Lei

All rights reserved.

Redistribution and use in source and binary forms, with or without
modification, are permitted provided that the following conditions are met:

* Redistributions of source code must retain the above copyright notice, this
  list of conditions and the following disclaimer.

* Redistributions in binary form must reproduce the above copyright notice,
  this list of conditions and the following disclaimer in the documentation
  and/or other materials provided with the distribution.

* Neither the name of SquareMeals nor the names of its
  contributors may be used to endorse or promote products derived from
  this software without specific prior written permission.

THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE
FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
