# ToggleButtonGroup

[![API](https://img.shields.io/badge/API-15%2B-brightgreen.svg?style=flat)](https://android-arsenal.com/api?level=15)

A group of toggle buttons, supports multiple / single selection. 

## Gradle

```
dependencies {
    compile 'com.nex3z:toggle-button-group:0.1.3'
}
```

## MultiSelectToggleGroup

You can create a group of multiple selection toggle buttons with `MultiSelectToggleGroup`.

<img src="images/multi.gif" height="75" />

Define the `MultiSelectToggleGroup` as follows:

```xml
<com.nex3z.togglebuttongroup.MultiSelectToggleGroup
    android:id="@+id/multi_selection_group"
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    android:textSize="16sp"
    app:buttonWidth="40dp"
    app:buttonHeight="40dp"
    app:textButtons="@array/weekdays"
    app:uncheckedTextColor="@android:color/black"
    app:checkedTextColor="@android:color/white"
    app:animationEnabled="true"/>
```

Use `textButtons` attribute to add buttons with the text from the specified string array. 

```xml
<resources>
    <string-array name="weekdays">
        <item>S</item>
        <item>M</item>
        <item>T</item>
        <item>W</item>
        <item>T</item>
        <item>F</item>
        <item>S</item>
    </string-array>
</resources>
```

## SingleSelectToggleGroup

You can create a group of single selection toggle buttons with `SingleSelectToggleGroup`.

<img src="images/single.gif" height="75" />

Define the `SingleSelectToggleGroup` as follows:

```xml
<com.nex3z.togglebuttongroup.SingleSelectToggleGroup
    android:id="@+id/single_selection_group"
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    android:textSize="16sp"
    app:buttonWidth="40dp"
    app:buttonHeight="40dp"
    app:uncheckedTextColor="@android:color/black"
    app:checkedTextColor="@android:color/white"
    app:animationEnabled="true"/>
```

You can also use `setButtons(List<String> text)` to add buttons to the group:

```java
SingleSelectToggleGroup singleSelect = (SingleSelectToggleGroup) findViewById(R.id.single_selection_group);
List<String> choices = Arrays.asList("A", "B", "C", "D");
singleSelect.setButtons(choices);
```

For both `MultiSelectToggleGroup` and `SingleSelectToggleGroup`, `getCheckedPositions()` will get the checked buttons' positions.

## Listener

Use `OnCheckedChangeListener` to listen to any button's checked state.

```java
singleSelect.setOnCheckedChangeListener(new ToggleButtonGroup.OnCheckedChangeListener() {
    @Override
    public void onCheckedChange(int position, boolean isChecked) {
        // ...
    }
});
```

You can also use `OnCheckedPositionChangeListener` to retrieve all checked buttons' positions when there is any change, which is more handy for `MultiSelectToggleGroup`.

```java
multiSelect.setOnCheckedPositionChangeListener(new ToggleButtonGroup.OnCheckedPositionChangeListener() {
    @Override
    public void onCheckedPositionChange(Set<Integer> checkedPositions) {
        //...
    }
});

```

## Customization

The toggle button group can be customized with the following attributes.

| Attribute                          | Format    | Description                                                                                                                                 |
|------------------------------------|-----------|---------------------------------------------------------------------------------------------------------------------------------------------|
| android:textSize                   | dimension | Size of the text.                                                                                                                           |
| android:saveEnabled                | boolean   | Controls whether the saving of this view's state is enabled. The default is false, which disables state saving while recreating.            |
| app:buttonWidth                    | dimension | Width of the button.                                                                                                                        |
| app:buttonHeight                   | dimension | Height of the button.                                                                                                                       |
| app:checkedBackground              | integer   | Sets a drawable (resource id) as the background image, which is shown when the button is checked and hidden when unchecked.                 |
| app:checkedTextColor               | color     | Color of the text when the button is checked.                                                                                               |
| app:uncheckedTextColor             | color     | Color of the text when the button is unchecked.                                                                                             |
| app:spacing                        | dimension | Spacing between neighboring buttons.                                                                                                        |
| app:animationEnabled               | boolean   | Sets whether the animation for toggling button is enabled. The default is `false`, meaning that the animation is disabled.                  |
| app:animationDuration              | integer   | Sets the duration of the animation for toggling button in milliseconds. The default is 150 milliseconds.                                    |
| app:textButton1<br>app:textButton2 | string    | Add a button with the specified text. If you want to add more than 2 buttons, use `textButtons` attribute or `setButtons()` method instead. |
| app:textButtons                    | reference | The string array to find the value for buttons' text                                                                                        |

## Licence

```
Copyright 2016 nex3z

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
