-- Active: 1763745673073@@127.0.0.1@5432@musicplayerdb
--creates database for musicplayerdb;
CREATE DATABASE musicplayerdb;

--creates schema for music player database;
CREATE SCHEMA musicPlayer; 

DROP SCHEMA musicPlayer CASCADE;

CREATE SCHEMA musicPlayer;

INSERT INTO musicPlayer.Artist (artist_name) VALUES
('Aurora Skye'),('Neon Harbor'),('The Midnight Bloom'),('Silver Atlas'),('Crimson Tide'),
('Velvet Echo'),('Echo Strand'),('Paper Lanterns'),('Moss & Marble'),('Golden Relay'),
('Polar Drift'),('Scarlet Orchard'),('Lantern Choir'),('Hollow Roads'),('Glass Sparrow'),
('Platinum River'),('Obsidian Meadow'),('Copper Skies'),('Maple Static'),('Ivory Compass'),
('Jade Horizon'),('Solar Fable'),('Lunar Threads'),('Velour Station'),('Cobalt Canvas'),
('Silent Meridian'),('Amber Circuit'),('Quartz Parade'),('Garnet Avenue'),('Willow Fringe'),
('Birch & Stone'),('Neptune''s Wake'),('Paper Kites'),('Rust & Ribbon'),('Cerulean Bloom'),
('Orchid Alley'),('Saffron Winds'),('Steel Carousel'),('Ivory Lanterns'),('Crystal Orchard'),
('Onyx Harbor'),('Maple Echo'),('Seaborn Mosaic'),('Frosted Vale'),('River & Rune'),
('Mango Skyline'),('Plume & Ash'),('Velvet Harbor'),('Copper Echo'),('Phoenix Grove');

-- Insert 50 albums (associate albums to artists by id 1..50)
INSERT INTO musicPlayer.Album (album_name, artist_id) VALUES
('Dawn Chorus',1),('Neon Nights',2),('Blooming Hours',3),('Atlas Rising',4),('Tide Lines',5),
('Echoes in Velvet',6),('Strand of Days',7),('Lantern Stories',8),('Moss Motifs',9),('Relay Sessions',10),
('Polar Lights',11),('Orchard Songs',12),('Choir of Lanterns',13),('Roadside Hymns',14),('Sparrow Tales',15),
('Riverwalk',16),('Meadow Songs',17),('Skyline Letters',18),('Static Whispers',19),('Compass Points',20),
('Horizon Days',21),('Solar Nights',22),('Lunar Shadows',23),('Station Stories',24),('Cobalt Dreams',25),
('Meridian Echoes',26),('Amber Days',27),('Parade of Lights',28),('Avenue No.9',29),('Fringe Lines',30),
('Stone & Birch',31),('Wake of Neptune',32),('Kite Strings',33),('Rust Reverie',34),('Cerulean Skies',35),
('Alley of Orchids',36),('Saffron Tales',37),('Carousel Rust',38),('Lantern Nights',39),('Crystal Skies',40),
('Harbor Songs',41),('Maple Ballads',42),('Mosaic Waves',43),('Frosted Hymns',44),('Runes & Rivers',45),
('Skyline Mango',46),('Ashen Plume',47),('Harbor Velvet',48),('Echoes of Copper',49),('Grove of Phoenix',50);

-- Insert 50 songs (title, artist_id, album (album name string))
INSERT INTO musicPlayer.Songs (title, artist_id, album_id) VALUES
('Sunrise Avenue', 1, 1),
('Midnight Cruise', 2, 2),
('Petal Drift', 3, 3),
('Cartographer', 4, 4),
('Saltwater Lullaby', 5, 5),
('Soft Static', 6, 6),
('Shoreline', 7, 7),
('Paper Moon', 8, 8),
('Greenroom', 9, 9),
('Relay Fade', 10, 10),
('Glacial Heart', 11, 11),
('Applebone', 12, 12),
('Lantern Glow', 13, 13),
('Dusty Highways', 14, 14),
('Feathered Skies', 15, 15),
('Riverbend', 16, 16),
('Stone Garden', 17, 17),
('Copper Letters', 18, 18),
('Silent Signal', 19, 19),
('North Star', 20, 20),
('Haze Line', 21, 21),
('Solar Drift', 22, 22),
('Moonlit Thread', 23, 23),
('Platform Five', 24, 24),
('Cobalt Sun', 25, 25),
('Meridian Road', 26, 26),
('Amberlight', 27, 27),
('Parade Lights', 28, 28),
('Ninth Street', 29, 29),
('Fringe Dance', 30, 30),
('Birch Waltz', 31, 31),
('Wake Song', 32, 32),
('Kite Runner', 33, 33),
('Rust Anthem', 34, 34),
('Cerulean Bloom', 35, 35),
('Orchid Whisper', 36, 36),
('Saffron Roads', 37, 37),
('Spinning Steel', 38, 38),
('Lantern Harbor', 39, 39),
('Crystal River', 40, 40),
('Harbor Light', 41, 41),
('Maple Avenue', 42, 42),
('Mosaic Drift', 43, 43),
('Frost Line', 44, 44),
('River Rune', 45, 45),
('Mango Breeze', 46, 46),
('Ash Plume', 47, 47),
('Velvet Dusk', 48, 48),
('Copper Echo', 49, 49),
('Phoenix Rise', 50, 50);
