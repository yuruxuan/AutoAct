input keyevent KEYCODE_MENU
sleep 2
input keyevent KEYCODE_MENU
sleep 5
input keyevent KEYCODE_HOME
sleep 2
am start -n com.jingdong.app.mall/.main.MainActivity
sleep 15
input tap 1300 2350
sleep 5
input tap 1300 2350
sleep 5
input tap 180 1050
sleep 5
input tap 720 600
sleep 5
am force-stop com.jingdong.app.mall
sleep 5