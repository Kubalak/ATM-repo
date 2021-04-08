# ATM-repo
Atm project repository currently v0.5.3.1

# Changelog v0.5.3.1
It is now possible to work with different user / card however you have to change it manually in the `userdata/settings.xml`.<br/>
Current user name and surname are displayed int the title of the window<br>
Information about ongoing operation are now shown on the <b>center</b> `JPanel`<br>
`settings.xml` now stores information about the currency of the machine.<br>

## Tweaks
Changed behaviour while trying to insert card that is already inserted.<br>
Machine state is saved after every user action.<br>
Created class `XMLTools` that provide basic XML utilities.

## New functions:
Checking account balance.<br>
User can set manually withdraw value.<br>

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
