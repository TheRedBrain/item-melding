# Merged Items

Adds a merging mechanic for items. Items can "store" other items and will inherit their attribute modifiers.

The main mechanic of this mod is heavily inspired by the 'Melding' mechanic in the game [Into the Necrovale](https://store.steampowered.com/app/1717090/Into_the_Necrovale/).

Values of similar attribute modifiers are averaged. This can be changed to a simple addition in the server config.

Merged items must be unstackable and can't contain merged items.

Items can optionally define an item tag, which determines what items can be merged into them.

The mod provides a simple block (found in the 'Operator Items' tab in the creative menu) that opens the default 'Item Merging Screen'. This screen allows merging of two items and also splitting merged items.

The default version of the screen can merge only 2 items together. It allows to merge items into every item with the MergedItemsComponent, however.

The mod provides a simple API for mods to open customized 'Item Merging Screens'.

The customization options include the maximum amount of merged items in one item, the screen title and a list of item tags, which determine what items can be used as container items.
The screen also displays which item tags are defined.

## Trinkets Integration

TrinketItems can be merged with other TrinketItems.

> Note, that merging "normal" items with TrinketItems works, but the attributes are not inherited.
