# ToggleButtonGroup

[![API](https://img.shields.io/badge/API-15%2B-brightgreen.svg?style=flat)](https://android-arsenal.com/api?level=15)

A container of toggle buttons, supports multiple / single selection and button customization.


## Gradle

```
dependencies {
    compile 'com.nex3z:toggle-button-group:1.0.0'
}
```

There has been a lot of changes since the 1.0.0 version, old versions can be found in the [release page](https://github.com/nex3z/ToggleButtonGroup/releases).


## SingleSelectToggleGroup

<div align="center">
  <img src="images/single.gif" height="75" />
</div>

You can create a group of single-select toggle buttons with `SingleSelectToggleGroup`.

```xml
<com.nex3z.togglebuttongroup.SingleSelectToggleGroup
    android:id="@+id/group_choices"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    app:tbgCheckedButton="@+id/choice_a">

    <com.nex3z.togglebuttongroup.button.CircularToggle
        android:id="@+id/choice_a"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="A"/>

    <com.nex3z.togglebuttongroup.button.CircularToggle
        android:id="@+id/choice_b"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="B"/>

       <!--...-->

</com.nex3z.togglebuttongroup.SingleSelectToggleGroup>
 ```


## MultiSelectToggleGroup

<div align="center">
  <img src="images/multi.gif" height="75" />
</div>

You can create a group of multi-select toggle buttons with `MultiSelectToggleGroup`.

```xml
<com.nex3z.togglebuttongroup.MultiSelectToggleGroup
    android:id="@+id/group_weekdays"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    app:tbgChildSpacing="auto">

    <com.nex3z.togglebuttongroup.button.CircularToggle
        android:id="@+id/sun"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="S"/>

    <com.nex3z.togglebuttongroup.button.CircularToggle
        android:id="@+id/mon"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="M"/>

    <!--...-->

</com.nex3z.togglebuttongroup.MultiSelectToggleGroup>
```


## Flow Buttons to Next Row

<div align="center">
  <img src="images/tags.gif"/>
</div>

```xml
<com.nex3z.togglebuttongroup.MultiSelectToggleGroup
    android:id="@+id/group_dummy"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:layout_marginTop="16dp"
    app:tbgFlow="true"
    app:tbgChildSpacing="auto"
    app:tbgChildSpacingForLastRow="align"
    app:tbgRowSpacing="8dp">

    <!--...-->

</com.nex3z.togglebuttongroup.MultiSelectToggleGroup>
```

With `tbgFlow` attribute set to `true`, buttons are allowed to flow to next row when there is no enough space in current row. With `tbgButtonSpacing` set to `auto`, buttons are evenly placed in each row.


## Listeners

For `SingleSelectToggleGroup`, use `OnCheckedChangeListener` to listen to the change of the checked button. Use `getCheckedId()` to get the id of the checked button.

```java
SingleSelectToggleGroup single = (SingleSelectToggleGroup) findViewById(R.id.group_choices);
single.setOnCheckedChangeListener(new SingleSelectToggleGroup.OnCheckedChangeListener() {
    @Override
    public void onCheckedChanged(SingleSelectToggleGroup group, int checkedId) {

    }
});
```

For `MultiSelectToggleGroup`, use `OnCheckedStateChangeListener` to be notified of any changes in the group. Use `getCheckedIds()` to get the ids of all checked buttons.

```java
MultiSelectToggleGroup multi = (MultiSelectToggleGroup) findViewById(R.id.group_weekdays);
multi.setOnCheckedChangeListener(new MultiSelectToggleGroup.OnCheckedStateChangeListener() {
    @Override
    public void onCheckedStateChanged(MultiSelectToggleGroup group, int checkedId, boolean isChecked) {

    }
});
```

## Custom Button

<div align="center">
  <img src="images/cards.gif"/>
</div>

You can implement custom toggle button in two ways,

- Implement [`ToggleButton`](https://github.com/nex3z/ToggleButtonGroup/blob/master/togglebuttongroup/src/main/java/com/nex3z/togglebuttongroup/button/ToggleButton.java) interface, as [`CustomToggleButton`](https://github.com/nex3z/ToggleButtonGroup/blob/master/sample/src/main/java/com/nex3z/togglebuttongroup/sample/CustomToggleButton.java) in the sample, or

- Extend [`CompoundToggleButton`](https://github.com/nex3z/ToggleButtonGroup/blob/master/togglebuttongroup/src/main/java/com/nex3z/togglebuttongroup/button/CompoundToggleButton.java) class, like [`CustomCompoundToggleButton`](https://github.com/nex3z/ToggleButtonGroup/blob/master/sample/src/main/java/com/nex3z/togglebuttongroup/sample/CustomCompoundToggleButton.java).

If you choose to implement `ToggleButton`, besides the [`Checkable`](https://developer.android.com/reference/android/widget/Checkable.html) interface, you also need to implement the `setOnCheckedChangeListener()`, which allows [`ToggleButtonGroup`](https://github.com/nex3z/ToggleButtonGroup/blob/master/togglebuttongroup/src/main/java/com/nex3z/togglebuttongroup/ToggleButtonGroup.java) to listen to changes of any checked states. You need to handle the click event on the button and toggle its checked state properly, as `ToggleButtonGroup` will not toggle the button when it's being clicked.

`CompoundToggleButton` implements `ToggleButton` and notifies `ToggleButtonGroup` of any checked state changes. But you still need to handle the click event on the button, as `CompoundToggleButton` wouldn't know which part of it should trigger the "toggle" operation. Add an `OnClickListener` on the "trigger view" and call `toggle()` is just enough.

For more detail, please check the [sample](https://github.com/nex3z/ToggleButtonGroup/tree/master/sample/src/main/java/com/nex3z/togglebuttongroup/sample).

