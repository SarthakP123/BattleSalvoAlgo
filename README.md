# BattleSalvoAlgo
BattleSalvo: An Evolution of Classic Battleship
Introducing BattleSalvo: a captivating twist on the classic game of Battleship. Unlike its predecessor, BattleSalvo offers an array of customizable game board dimensions, ranging from 6 to 15 in both height and width. These dimensions need not be uniform, allowing for a truly personalized gaming experience.
In BattleSalvo, diversity reigns supreme. While Battleship adheres to a strict one-boat-per-type policy, BattleSalvo embraces variety by permitting multiple instances of each boat category. It's a game where strategic thinking and adaptability take center stage.Yet, even in this realm of boundless possibilities, there are rules to uphold. The collective size of your fleet must not surpass the smaller dimension of your chosen board, ensuring a fair and balanced gameplay. Each boat type must be represented, offering players an equitable foundation for their naval conquests. Picture a board of 8x11 dimensions—a canvas for tactical brilliance. Here, a maximum of 8 boats can grace the waters, making for an exhilarating contest of wits. Brace yourself for a BattleSalvo experience like no other, where every move is a calculated step toward victory.

**Algorithm Overview*****
**Hunt Mode**
In the initial phase, the AI operates in "hunt mode." It strategically selects diagonal shots in regions with the highest probability of containing enemy ships.
If a shot lands successfully and hits a ship, the algorithm transitions to "target mode." If the shot misses, it continues hunting.

**Target Mode**
Once a ship is hit, the algorithm identifies the orientation of the ship by firing shots in all directions around the hit location.
After determining the ship's orientation, the algorithm focuses its efforts on targeting the ship with a sequence of three consecutive shots in the direction of the ship's alignment.
Any remaining shots are used to explore the surrounding area for other potential ship locations.

**Heat Zones**
The algorithm leverages the concept of "heat zones" when there is high confidence that a ship is positioned between two hit locations.
In such cases, a specialized "heat zone" class is activated, which concentrates fire in the area between the two hits, expediting the destruction of the ship.

**Handling Ship Sizes**
Given that the smallest ship size is three squares, the algorithm's approach is tailored to efficiently eliminate ships of this size or larger.

**Adaptive Heatmap**
To guide its targeting decisions, the algorithm maintains a heatmap that continuously updates the probability distribution of ship placements on the game board.
The heatmap evolves with each turn, reflecting the changing landscape of potential ship locations.

**Usage**
To utilize this algorithm in your Battleship game, simply integrate the provided code into your project. The algorithm is designed to enhance the AI's targeting strategy, leading to more effective gameplay.


