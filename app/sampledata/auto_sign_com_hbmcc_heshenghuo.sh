input keyevent KEYCODE_MENU
sleep 2
input keyevent KEYCODE_MENU
sleep 5
input keyevent KEYCODE_HOME
sleep 2
am start -n com.hbmcc.heshenghuo/.ui.welcome.WelcomeActivity
sleep 15
input keyevent KEYCODE_BACK
sleep 5
input tap 1400 1560
sleep 5
input tap 1300 1300
sleep 10
input tap 700 1830
sleep 5
am force-stop com.hbmcc.heshenghuo
sleep 5