# ToggleButtonGroup

A group of toggle buttons, supports multiple / single selection. 

## Gradle

```
dependencies {
    compile 'com.nex3z:toggle-button-group:0.1.0'
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
    app:uncheckedTextColor="@android:color/black"
    app:checkedTextColor="@android:color/white"
    app:enableAnimation="true"/>
```

Then use `setButtons()` to add buttons to the group:

```java
MultiSelectToggleGroup multiSelect = (MultiSelectToggleGroup) findViewById(R.id.multi_selection_group);
List<String> weekdays = Arrays.asList("S", "M", "T", "W", "T", "F", "S");
multiSelect.setButtons(weekdays);
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
    app:enableAnimation="true"/>
```

Then use `setButtons()` to add buttons to the group:

```java
SingleSelectToggleGroup singleSelect = (SingleSelectToggleGroup) findViewById(R.id.single_selection_group);
List<String> choices = Arrays.asList("A", "B", "C", "D");
singleSelect.setButtons(choices);
```