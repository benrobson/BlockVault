# BlockVault

![img](https://i.imgur.com/DPcMoaq.png)

BlockVault is an interactive challenge where players collaborate to collect every unique block and item in Minecraft. The centerpiece is a large, dynamic museum building where contributions are tracked, and progress is displayed, turning the collection into an engaging and rewarding experience.

## Features
### Interactive Block Displays
* Sections dedicated to specific block categories (e.g., wood, ores, decorative blocks).
* Blocks and items can be placed on predefined slots with item frames or display stands, indicating where each item belongs and can be contributed to.

### Points System
* Players earn points for each block they unlock and contribute to the museum. 
* The points awarded vary based on the rarity of the block/item:
  * **Common Blocks:** 1 point per block.
  * **Uncommon Blocks:** 5 points per block.
  * **Rare Blocks/Items:** 10+ points per block.
* A running total of points is tracked for each player, contributing to their leaderboard position.

### Tracking Progress
* A real-time leaderboard displays the top contributors and their collected blocks/items.
* A progress bar or percentage indicator shows how close the server is to completing the collection.

### Progress Leaderboard & GUI Integration
* Progress can be viewed in real-time using a hologram display, showcasing the top contributors and collection milestones.
* Use the `/vault leaderboard` command to see a detailed leaderboard of top contributors, including points and blocks contributed.
* GUI integration allows players to easily browse and view all collected blocks in an organized interface.

### Broadcast Announcements
* **Rare Blocks/Items Announcements:** Announce server-wide when a rare block or item is added to the vault, celebrating player contributions and encouraging participation.
* **Milestone Achievements:** Broadcast messages when major milestones are reached, such as completing 50% of the vault or finishing specific block categories.

### Timer and Boss Integration
* A countdown timer can be enabled to add urgency, ticking down the time remaining for the challenge.
* A boss integration shows live updates on:
  * The time elapsed.
  * How many blocks have been collected.
  * How much time is left (if the timer is enabled).

## Commands
* `/vault start` – Starts the vault challenge (requires `blockvault.start`).
* `/vault generate` – Generates the vault based on the schematic (requires `blockvault.generate`).
* `/vault add <item/block>` – Manually adds a block/item to the collection.
* `/vault progress` – Displays the overall progress of the collection and the leaderboard.
* `/vault leaderboard` – Shows the top contributors and their points in a detailed list.