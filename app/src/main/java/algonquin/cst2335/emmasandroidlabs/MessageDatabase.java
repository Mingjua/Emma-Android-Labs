package algonquin.cst2335.emmasandroidlabs;

import androidx.room.Database;
import androidx.room.RoomDatabase;

    // This class should just have one abstract function
    // which returns the DAO for interacting with this database.

    // This tells Room that this Database class is meant for storing ChatMessage objects,
    // and uses the ChatMessageDAO class for querying data

    // The version parameter is used in case you ever need to change the structure of the ChatMessage class
    @Database(entities = {ChatMessage.class}, version=1)
    public abstract class MessageDatabase  extends RoomDatabase {
        public abstract ChatMessageDAO cmDAO();
    }
