package iitg.campusevents;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class databaseConn {
	// Database fields
	private SQLiteDatabase database;
	private MySQLiteHelper dbHelper;
	private String[] allColumns = { MySQLiteHelper.COLUMN_ID,
			MySQLiteHelper.COLUMN_COMMENT };

	public databaseConn(Context context) {
		dbHelper = new MySQLiteHelper(context);
	}

	public void open() throws SQLException {
		database = dbHelper.getWritableDatabase();
	}

	public void close() {
		dbHelper.close();
	}

	/*public Events createComment(String comment) {
		ContentValues values = new ContentValues();
		values.put(MySQLiteHelper.COLUMN_COMMENT, comment);
		long insertId = database.insert(MySQLiteHelper.TABLE_COMMENTS, null,
				values);
		Cursor cursor = database.query(MySQLiteHelper.TABLE_COMMENTS,
				allColumns, MySQLiteHelper.COLUMN_ID + " = " + insertId, null,
				null, null, null);
		cursor.moveToFirst();
		Comment newComment = cursorToComment(cursor);
		cursor.close();
		return newComment;
	}

	public void deleteComment(Comment comment) {
		long id = comment.getId();
		System.out.println("Comment deleted with id: " + id);
		database.delete(MySQLiteHelper.TABLE_COMMENTS, MySQLiteHelper.COLUMN_ID
				+ " = " + id, null);
	}*/

	public List<Events> getAllEvents() {
		List<Events> events = new ArrayList<Events>();

		Cursor cursor = database.query(MySQLiteHelper.TABLE_COMMENTS,
				allColumns, null, null, null, null, null);

		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			Events event = cursorToEvent(cursor);
			events.add(event);
			cursor.moveToNext();
		}
		// Make sure to close the cursor
		cursor.close();
		return events;
	}
 
	

	private Events cursorToEvent(Cursor cursor) {
		Events event = new Events();
		event.eventId = cursor.getLong(0);
		
		//Check
		//event.startTime = Date(cursor.getString(1));
		//event.endTime = Date(cursor.getString(2));
		//event.modifiedTime = Date(cursor.getString(3));
		
		event.title = cursor.getString(4);
		event.content = cursor.getString(5);
		event.locationId = cursor.getLong(6);
		event.categoryId = cursor.getLong(7);
		event.postedBy = cursor.getLong(8);
		event.status = cursor.getString(9);
		
		return event;
	}
}
