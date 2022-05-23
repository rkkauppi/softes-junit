package fi.haagahelia.course;

import java.util.List;
import model.ShoppingListItem;

import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

class ShoppingListAddRemove {
	
	@Test
	void testRemoveAddedItem() {
		database.ShoppingListItemDao dao = new database.FakeShoppingListItemDao();
		
		// check the precondition
		List<ShoppingListItem> items = dao.getAllItems();
		boolean milkFound = false;
		boolean eggsFound = false;
		boolean breadFound = false;
		ShoppingListItem bread = null;
		
		for (int i = 0; i < items.size(); i++) {
			String title = items.get(i).getTitle();
			if (title.equals("Milk")) {
				milkFound = true;
			} else if (title.equals("Eggs")){
				eggsFound = true;
			} else if (title.equals("Bread")){
				breadFound = true;
				bread = items.get(i);
			}
		}
		
		assertTrue(milkFound && eggsFound && breadFound, "Milk, eggs or bread missing");
		
		// remove bread
		dao.removeItem(bread);
		
		// check that removal lead to the expected outcome
		breadFound = false;
		
		for (int i = 0; i < items.size(); i++) {
			String title = items.get(i).getTitle();
			if (title.equals("Bread")) {
				breadFound = true;
			}
		}
		
		assertTrue(!breadFound, "Bread was not removed");
	}
	
	@Test
	void testAddFirstItem() {
		database.ShoppingListItemDao dao = new database.FakeShoppingListItemDao();
		
		// make sure that the shoppinglist is empty
		// shoppinglist id:s start from 1

		List<ShoppingListItem> items = dao.getAllItems();
			
		int size = items.size();
			
		for (int i = 1; i <= size; i++) {
			dao.removeItem(dao.getItem(i));
		}
		
		// check the precondition
		assertTrue(dao.getAllItems().size() == 0, "Shoppinlist is not empty");
		
		// create bread
		ShoppingListItem bread = new ShoppingListItem(1, "Bread");
		
		// add bread
		dao.addItem(bread);
		
		// search the added bread
		ShoppingListItem addedItem = null;
		
		items = dao.getAllItems();
		
		for (int i = 0; i < items.size(); i++) {
			if (items.get(i).getTitle().equals("Bread")) {
				addedItem = items.get(i);
			}
		}
				
		// check that adding lead to the expected outcome
		assertTrue(addedItem != null, "Item was not added");
		assertTrue(addedItem.getId() == 1, "Id is not 1");
		assertTrue(addedItem.getTitle().equals("Bread"), "Title is not Bread");
	}
}
