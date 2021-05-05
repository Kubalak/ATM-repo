# ATM-repo
Atm project repository.

# Changelog v0.7.0
Added textures for buttons. Each button has a texture when pressed and when not pressed.<br>


# Changelog v0.6.0
Created new class `Sound` that operates all necessary sound methods for the app.<br>
Started writing <a href = "https://junit.org"><b>JUnit5</b></a> tests.

## Tweaks
`Window` class now uses objects of the `Sound` class to play certain sound or background music.
`StateManager` class now uses methods of the `Sound` class to play certain sound after sucesfull deposit or withdraw.
Method <i>returnCard()</i> now returns card in every state.

# Changelog v0.5.5.0
Created new class `Settings` that holds all the settings for the app.<br>
Changed `StateManager` class methods structure to make code less redundant.<br>
Method <i>sendSignal()</i> has been reorganized to make it shorter.

## Tweaks
`Window` class now accesses users via `Settings` class fields.<br>
`Users` class array has been changed to `Vector<User>` as well as `CreditCard` array.

## New Functions:
In the <i>SUMMARY</i> state after selecting <i>Yes</i> in the confirmation quote results are printed to file `dd.mm.yyy_hhmmss` format.

# Changelog v0.5.4.0
Fixed a bug with -1 value of banknotes in the user wallet after changing working wallet contents.<br>
Position of a window is now saved to the `userdata/settings.xml` file.<br>
Started creating code documentation.<br>
Project is ready to be get textures, sounds, etc. involved.<br>
Changing user and card is now supported.

## Tweaks
Classes `Main`,`StateManager`,`Window` have been moved to different package.<br>
Class `XMLTools` has been changed to `abstract` in order to prevent accidental trial of creating an object of this class.
In the `OP_SEL` state Labels have been moved to the left.

## New functions:
Window position is now saved to the settings file.<br>
You can check whether machine state has changed or not.<br>
It is now possible to check between what states machine is switching.<br>
`Wallet` class now allows creating a copy of an existing object or to copy contents of another object (in order to prevent bugs in the future).<br>
It is now possible to change your PIN code as a user.<br>
It is now possible to change card and user from the menu.

# Changelog v0.5.3.1
It is now possible to work with different user / card however you have to change it manually in the `userdata/settings.xml`.<br/>
Current user name and surname are displayed int the title of the window<br>
Information about ongoing operation are now shown on the <b>center</b> `JPanel`<br>
`settings.xml` now stores information about the currency of the machine.<br>
`StateManager` contains 5 different `JLabeles` associated with ATM window parts.<br> 

## Tweaks
Changed behaviour while trying to insert card that is already inserted.<br>
Machine state is saved after every user action.<br>
Created class `XMLTools` that provide basic XML utilities.<br>
`StateManager` now needs two arguments in <i>sendSignal</i> function.<br>
This change is made due to the fact that deposit function needs a `Wallet` class object.

## New functions:
User can set manually withdraw value.<br>
User can now deposit money (<b><i>work in progress</i></b>).

# Changelog v0.5.2.0
Users and their data are now stored in `userdata/settings.xml` file.<br/>
Loading and saving in XML format is now supported.<br/>
Changes in user data are saved by using the MenuBar <b>Save</b> option.<br/>

## Tweaks:
In the `User` class some fields have been renamed.<br/>

# Changelog v0.5.1.1
Created classes:<br>
<b>`User`</b> that stores user details including his name and surname also user cards are stored in here.<br>
<b>`Wallet`</b> that stores banknotes and the amount of them.<br>
<b>`CreditCard`</b> that stores card info such as PIN code and information about a lock on the card.<br>
<b>`Account`</b> that stores information about account balance</b>
<br>
## New functions:<br/>
Money withdrawal.<br/>
Card insertion / removal.<br/>
Account balance check.<br/>
Card now locks after three unsuccessfull attempts.<br/>
<br/>
## Tweaks:<br/>
Inserting cards and PIN codes now work in different order.<br/>
Changed states of machine available (after the `PIN` state goes the `SEL_OP` state that allows to select next operation).<br/>
