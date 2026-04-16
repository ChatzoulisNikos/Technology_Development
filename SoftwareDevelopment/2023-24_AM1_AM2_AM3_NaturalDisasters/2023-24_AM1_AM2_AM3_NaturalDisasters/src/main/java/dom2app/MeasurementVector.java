package dom2app;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;
import org.apache.commons.math3.stat.regression.SimpleRegression;
import org.apache.commons.math3.util.Pair;

public class MeasurementVector implements IMeasurementVector {
	String ObjectId;
	String countryName;
	String ISO2;
	String ISO3;
	String indicator;
	List<Pair<Integer ,Integer>> measurements;
	
	
	
	
	
	public MeasurementVector(String ObjectId, String countryName , String ISO2, String ISO3, String indicator , List<Pair<Integer,Integer>> measurements){
		this.ObjectId=ObjectId;
		this.countryName=countryName;
		this.ISO2=ISO2;
		this.ISO3=ISO3;
		this.indicator=indicator;
		this.measurements=measurements;
	}
	
	public String getObjectId() {
		return ObjectId;
	}
	public String getISO2() {
		return ISO2;
	}
	public String getISO3() {
		return ISO3;
	}
	
	public String getCountryName() {
		return countryName;
	}
	
	public String getIndicatorString() {
		return indicator;
	
	}
	
	 public List<Pair<Integer ,Integer>> getMeasurements(){
		 return measurements;
	 }
	 
	 public String getDescriptiveStatsAsString() {
//		 DescriptiveStatistics stats =new DescriptiveStatistics();
//		 List<Pair<Integer,Integer>> pair=getMeasurements();
//		 for(Pair<Integer, Integer> xyPair : pair) {
//			 int year=xyPair.getFirst();
//			 int value=xyPair.getSecond();
//			 stats.addValue(value);
//			 
//		 }
//		long count=stats.getN();
//		double minD = stats.getMin();
//		double gMean = stats.getGeometricMean();
//		double mean = stats.getMean();
//		double medianD = stats.getPercentile(50);
//		double maxD = stats.getMax();
//		double kurtosis = stats.getKurtosis();
//		double stdev = stats.getStandardDeviation();
//		double sumD = stats.getSum();
//		 
		 return null; //"count=" + count + " ," + "minD=" +minD + " ," +"gMean=" + gMean + " ," + "mean=" + mean + " ," +"medianD=" + medianD  + " ," +"maxD="+  maxD + " ," + "kurtosis=" +kurtosis + " ," + "stdev=" + stdev + " ," +"sumD=" +  sumD;
	 }
	 
	 
	public String getRegressionResultAsString() {
//			SimpleRegression regression = new SimpleRegression();
//			List<Pair<Integer, Integer>> pair=getMeasurements();
//			for(Pair<Integer,Integer> xyPair : pair) {
//				int year=xyPair.getFirst();
//				int value=xyPair.getSecond();
//				regression.addData(year,value);
//				
//			}
//			double intercept=regression.getIntercept();
//			double slope=regression.getSlope();
//			double slopeError=regression.getSlopeStdErr();
//			
//		return "intercept=" +intercept + " ," +"slope="+ slope + " ," +"SlopeError="+  slopeError ;
		return null;
	}
	

}
