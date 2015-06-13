

___________________________________________________________________________________________________________________

LINK TO DEMO VIDEO- https://www.youtube.com/watch?v=WXvOjMfTUmk

CONTROLS- Use the arrow keys to control pac man, he will automatically move in whatever direction he is
facing, provided there is not a wall in front of him. Do not hold them down, as the arrow keys do not respond as well to
being held down versus letter keys.

Press 'S' to start the game

KEY FEATURES- The game plays very true to the original Pac-Man, most notably in the ghost logic. The board consists of a
28 x 36 grid, which contains the playable space as well as life and score display. All 4 ghosts use the same logic to determine
their directions as they do in the original game, all code for ghost logic is original, based on several publications and websites
discussing the way they decide to move.

Ghosts have 3 modes: Scatter, Chase, and Frightened. They start the game in scatter mode for 7 seconds, then chase mode for 20, then
back to scatter for 7. This pattern continues for 3 cycles until they stay in perpetual chase mode.

When a PacMan eats an Energizer, one of the big dots, the ghosts enter Frightened mode and turn blue. As the levels progress,
the time that the ghosts stay frightened. When the ghosts are in frightened mode, they turn randomly.

When the ghosts are in Scatter mode, they each try to move as close to a pre-determined square as possible. When they are in chase mode
they each try to get as close to a certain target square as possible, more details on each ghosts logic are available on each ghost's
class file.

Ghosts make their decision one turn in advance. When they reach a square, they immediately look to the next square and decide
which way they will turn, if it all. Ghosts ONLY reverse direction when a mode change occurs, provided they have not just completed
a turn and are still in the same square.


BONUS LIFE- As in the original, the player is awarded a bonus life when their score reaches 10,000

FRUIT- After 70 dots, a cherry pops up and awards pac-man 100 bonus points if eaten.


In addition to these features, there are a couple of extra features:

Press 'O' for Ouija mode, this will summon an extra ghost for each time it's pressed. Avoid holding it down as ghosts spawned
    at exactly the same time will essentially act as one ghost, more on that later.

Press 'G' for Ghostbuster mode, this will give pac-man a 3 second proton-pack which will allow him to suck up ghosts and
    get him out of a jam. You only get 3 of these per game.

