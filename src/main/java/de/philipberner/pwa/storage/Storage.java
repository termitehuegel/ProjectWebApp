package de.philipberner.pwa.storage;

import de.philipberner.pwa.data.Data;

import java.net.Inet4Address;
import java.util.*;

/**
 * @author Philip Berner
 * @version 1.0
 */
public class Storage {

	/**
	 *  default data, that is used after the start of the program
	 */
    private static final Data[] defaultData = {
            new Data(0, "Max Mustermann", -3),
            new Data(1, "John Doe", 0 , "Testeintrag", 123123123),
            new Data(2, "Max Mustermann", 1000 , "Kommentar", 32323123123L),
            new Data(3, "Tim Müller", 90 , "Vielfraß", 23213123),
            new Data(4, "Mozart", 69 , "Mag Törtchen", 193973),
            new Data(5, "Popeye", 70 , "Spinat", 1000000L),
            new Data(6, "Donald Duck", Integer.MIN_VALUE, "reich", 20000),
            new Data(7, "Bach", -420 , "3",0),
            new Data(8, "Kommentarlos", Integer.MAX_VALUE),
            new Data(9, "", 1200, "Namenlos", 1000)
    };

	/**
	 * the list of all current data
	 */
    private static List<Data> data = new ArrayList<Data>(Arrays.asList(defaultData));
	
	/**
	 *  @return an Data array that has all current data
	 */
    public static Data[] getData() {
        return data.toArray(new Data[0]);
    }

	/**
	 *  @param data sets the data field
	 */
    public static void setData(List<Data> data) {
        Storage.data = data;
    }

	/**
	 * changes the data at a specific index
	 * @param index is the index of the changing element
	 * @param data is the data the index is set to
	 */
    private static void setDataAtIndex(int index, Data data) {
        Storage.data.set(index, data);
    }

	/**
	 *  gets data from a specific index
	 *  @param index is the index of the element that is returned
	 *  @return an Data object that is at the specific index
	 */
    private static Data getDataAtIndex(int index) {
        return Storage.data.get(index);
    }

	/**
	 *  searches the data for an id and finds the corresponding index
	 *  @param id is the id searched for
	 *  @return the index of the element or -1 if the element isn't found
	 */
    private static int getIndexOfId(int id) {
		//for every data in the Storage
        for (int i=0; i<Storage.data.size(); i++) {
			//if id is found
            if (Storage.data.get(i).getId() == id) {
                return i;
            }
        }
		//if id isn't found
        return -1;
    }

	/**
	 *  searches for data with specific id
	 *  @param id is the id of the data
	 *  @return a Data object that has the specified id
	 */
    public static Data getDataWithId(int id) {
        //gets the index of the specific id
		int index  = getIndexOfId(id);
		//short if: return data except when index<0
        return index >= 0 ? getDataAtIndex(index) : null;
    }

	/**
	 *  gets data with a specific name
	 *  @param name is the name searched for
	 *  @return an array of elements with the specified name
	 */
    public static Data[] getDataWithName(String name) {
		//result list where all the matches will be stored
        List<Data> result = new ArrayList<Data>();
		
		//for every data object in the data list
        for (Data data : getData()) {
			//if the name matches
            if (data.getName().equals(name)) {
                //adds the data to the list
				result.add(data);
            }
        }
		//returns the result as an array
        return result.toArray(new Data[0]);
    }

	/**
	 *  gets als data with specific money
	 *  @param money specifies the money amount
	 *  @return the matching data objects as an array
	 */
    public static Data[] getDataWithMoney(int money) {
		//result list
        List<Data> result = new ArrayList<Data>();
		
		//for every data object
        for (Data data : getData()) {
			//if money matches
			if (data.getMoney() == money) {
				//adds the data to the list
				result.add(data);
            }
        }
		//returns the result as an array
        return result.toArray(new Data[0]);
    }
	
	/**
	 *  adds data to the storage
	 *  @param data is the added data
	 *  @return success
	 */
    public static boolean addData(Data data) {
		//if the data doesn't exist
        if (getIndexOfId(data.getId()) < 0) {
			//adds the data
            Storage.data.add(data);
            return true;
        }
        return false;
    }

	/**
	 *  removes all data with the same id from storage
	 *  @param data is the data removed - id is important
	 *  @return success
	 */
    public static boolean removeData(Data data) {
		//gets the index the data element in the storage
        int index  = getIndexOfId(data.getId());
		//removes the data and returns success
        return removeData(index);
    }
	
	/**
	 *  removes data at a specific index
	 *  @param index is the index of the element in the storage
	 *  @return success
	 */
    private static boolean removeData(int index) {
        //if index exists
		if (index < data.size() && index >=0) {
			//remove data at index
            data.remove(index);
            return true;
        }
        return false;
    }
	
	/**
     *  changes a data element to a new data element - only id needs to match 
	 *  @param oldData has the id of the old data element
	 *  @param newData is the new data
	 *  @return success
	 */
    public static boolean changeData(Data oldData, Data newData) {
		//gets index of old data element
        int oldIndex  = getIndexOfId(oldData.getId());
		//gets index of new data element
        int newIndex = getIndexOfId(newData.getId());
		//if oldData exists in storage and new data doesn't or new data is old data
        if (oldIndex >= 0 && (newIndex < 0 || newIndex == oldIndex)) {
			//change data
			Storage.data.set(oldIndex, newData);
            return true;
        }
        return false;
    }

	/**
	 *  changes the name of data object
	 *  @param data the data to change
	 *  @param newName is the new name
	 *  @return success
	 */ 
    public static boolean changeName(Data data, String newName) {
		//gets the index of the data
        int index = getIndexOfId(data.getId());
		//if data exists in storage
        if (index >= 0) {
			//set new name
            Storage.data.get(index).setName(newName);
            return true;
        }
        return false;
    }

	/**
	 *  changes the name of a data object
	 *  @param data the data object
	 *  @param newMoney is new money
	 *  @return success 
	 */
    public static boolean changeMoney(Data data, int newMoney) {
        int index = getIndexOfId(data.getId());
		//if data exists in storage
        if (index >= 0) {
			//set new money
            Storage.data.get(index).setMoney(newMoney);
            return true;
        }
        return false;
    }
	
	/**
	 *  increases the money of a data object by an amount
	 *  @param data is the data
	 *  @param amount is the amount increased by
	 *  @return success
	 */
    public static boolean increaseMoney(Data data, int amount) {
        int index = getIndexOfId(data.getId());
        //if data exists in storage
		if (index >= 0) {
			//gets money of data
            int money = Storage.data.get(index).getMoney();
            //increases the money and sets it
			Storage.data.get(index).setMoney(money + amount);
            return true;
        }
        return false;
    }

	/**
	 *  changes the comment of a data object
	 *  @param oldData is the changed data
	 *  @param newComment is the new comment
	 *  @return success
	 */
    public static boolean changeComment(Data oldData, String newComment) {
        int index = getIndexOfId(oldData.getId());
		//if oldData exists
        if (index >= 0) {
			//change comment
            Storage.data.get(index).setComment(newComment);
            return true;
        }
        return false;
    }

}
