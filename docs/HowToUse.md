# How to use
The __ROOT__ Android device is required.  
Recommend: [TWRP](https://twrp.me/)+[Magisk](https://forum.xda-developers.com/apps/magisk/official-magisk-v7-universal-systemless-t3473445)  

## Step by step
### Step 1
When you first launch AutoAct, the root permission will be requested. You should click "GRANT" and continue to config next step. If the permission request denied uncarefully, you must enable root permission manually.  

![image](https://github.com/yuruxuan/AutoAct/raw/master/pics/3.png)  

### Step 2
Click "Change to system app", and reboot your device.

### Step 3
Create a new auto task. You can setup task name, description, scripts and so on. The script is important part in this step.  
For example, I want to launch Chrome 3 times everyday. The script will be set like this.
```
input keyevent KEYCODE_MENU
sleep 2
input keyevent KEYCODE_MENU
sleep 5
input keyevent KEYCODE_HOME
sleep 2
am start -n com.android.chrome/com.google.android.apps.chrome.Main
sleep 15
am force-stop com.android.chrome
```  
After that, you should pick the task first launch time and interval time. If you want to 3 times everyday, interval time will be set "8 Hours".

### Step 4
Click the OK button(seem like √ ) to finish setup a task.
