package database.datacollection;

import java.io.IOException;

/**
 * Head of the data collection program. This calls all other classes.
 */

public class DataCollectionMain {

	public static void main(String[] args) throws IOException {
		MongoStorage.setUp();
<<<<<<< HEAD
//		demoSodaCollector();
//		demoZomatoCollector();
//		demoSchoolCollector();
//		demoSensorDataCollector();
=======
		//demoSodaCollector();
		//demoZomatoCollector();
		//demoSchoolCollector();
		//demoSensorDataCollector();
>>>>>>> a24025247fb7a541743fce34269bda58c0312ebf
//		MongoStorage.saveCombinedZipcodeData();
	}

	private static void demoSodaCollector() {
		SodaCollector demo = new SodaCollector();
		try {
			DataSet ds = demo.getNewData();
			ds.printDataSet();
			MongoStorage.saveData(ds, MongoStorage.DataTypes.TRAFFIC_RAW_DATA);
		} catch (IOException e) {
			System.out.println(e.toString());
		}
	}
	
	private static void demoZomatoCollector() {
		ZomatoCollector demo = new ZomatoCollector();
		try {
			DataSet ds = demo.getNewData(0);
			ds.printDataSet();
			MongoStorage.saveData(ds, MongoStorage.DataTypes.FOOD_RAW_DATA);
		} catch (IOException e) {
			System.out.println(e.toString());
		}
	}

	private static void demoSchoolCollector() {
		SchoolCollector demo = new SchoolCollector();
		try {
			DataSet ds = demo.getNewData();
			ds.printDataSet();
			MongoStorage.saveData(ds, MongoStorage.DataTypes.EDUCATION_RAW_DATA);
		} catch (IOException e) {
			System.out.println(e.toString());
		}
	}
	
	private static void demoSensorDataCollector() {
		SodaCollector demo = new SodaCollector();
		try {
			DataSet ds = demo.getSensorInfoData();
			ds.printDataSet();
			MongoStorage.saveData(ds, MongoStorage.DataTypes.TRAFFIC_SENSOR_DATA);
		} catch (IOException e) {
			System.out.println(e.toString());
		}
	}
}
