# ATM-repo
Atm project repository currently v0.5.1.1

# Changelog v0.5.1.1
Created classes:<br>
<b>`User`</b> that stores user details including his name and surname also user cards are stored in here.<br>
<b>`Wallet`</b> that stores banknotes and the amount of them.<br>
<b>`CreditCard`</b> that stores card info such as PIN code and information about a lock on the card.<br>
<b>`Account`</b> that stores information about account balance</b>
<br>
## New functions:<br>
Money withdrawal.<br>
Card insertion / removal.<br>
Account balance check.<br>
Card now locks after three unsuccessfull attempts.<br>
<br>
## Tweaks:<br>
Inserting cards and PIN codes now work in different order.<br>
Changed states of machine available (after the `PIN` state goes the `SEL_OP` state that allows to select next operation).<br>
