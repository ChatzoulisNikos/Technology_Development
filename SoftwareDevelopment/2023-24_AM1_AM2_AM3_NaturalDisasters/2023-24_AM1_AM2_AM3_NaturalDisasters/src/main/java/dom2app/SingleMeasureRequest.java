package dom2app;


import java.util.List;

import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;
import org.apache.commons.math3.stat.regression.SimpleRegression;
import org.apache.commons.math3.util.Pair;

public class SingleMeasureRequest implements ISingleMeasureRequest {
	
	private String requestName;
	private String objectId;
	private String countryName;
	private String ISO2;
	private String ISO3;
	private String indicator;
	List<Pair<Integer,Integer>> requestPair;
	private boolean flag=false;
	private IMeasurementVector answer;
	
	
	public SingleMeasureRequest(String requestName,String objectId,  String countryName ,String ISO2, String ISO3, String indicator , List<Pair<Integer,Integer>> requestPair) {
		this.requestName=requestName;
		this.objectId=objectId;
		this.countryName=countryName;
		this.ISO2=ISO2;
		this.ISO3=ISO3;
		this.indicator=indicator;
		this.requestPair=requestPair;
		this.answer=new MeasurementVector(objectId, countryName,ISO2,ISO3,indicator,requestPair);
		this.flag=true;
		
	}
	

	
	@Override
	public String getRequestName() {
		// TODO Auto-generated method stub
		return requestName;
	}

	@Override
	public String getRequestFilter() {
		// TODO Auto-generated method stub
		return countryName + " " + indicator;
	}

	@Override
	public boolean isAnsweredFlag() {
		// TODO Auto-generated method stub
		if(answer!=null) {
			return flag;
		}
		return false;
	}

	@Override
	public IMeasurementVector getAnswer() {
	 return answer;
	}

	@Override
	public String getDescriptiveStatsString() {
		// TODO Auto-generated method stub
		DescriptiveStatistics stats =new DescriptiveStatistics();
		SimpleRegression regression = new SimpleRegression();
		
		 List<Pair<Integer,Integer>> pair=requestPair;
		 for(Pair<Integer, Integer> xyPair : pair) {
			 int year=xyPair.getFirst();
			 int value=xyPair.getSecond();
			 stats.addValue(value);
			 regression.addData(year,value);
			 
		 }
		long count=stats.getN();
		double minD = stats.getMin();
		double gMean = stats.getGeometricMean();
		double mean = stats.getMean();
		double medianD = stats.getPercentile(50);
		double maxD = stats.getMax();
		double kurtosis = stats.getKurtosis();
		double stdev = stats.getStandardDeviation();
		double sumD = stats.getSum();
		 
		 return "count=" + count + " ," + "minD=" +minD + " ," +"gMean=" + gMean + " ," + "mean=" + mean + " ," +"medianD=" + medianD  + " ," +"maxD="+  maxD + " ," + "kurtosis=" +kurtosis + " ," + "stdev=" + stdev + " ," +"sumD=" +  sumD;
	 
	}
	
	@Override
	public String getRegressionResultString() {
		SimpleRegression regression = new SimpleRegression();
		List<Pair<Integer, Integer>> pair=requestPair;
		for(Pair<Integer,Integer> xyPair : pair) {
			int year=xyPair.getFirst();
			int value=xyPair.getSecond();
			regression.addData(year,value);
			
		}
		double intercept=regression.getIntercept();
		double slope=regression.getSlope();
		double slopeError=regression.getSlopeStdErr();
		
	return "intercept=" +intercept + " ," +"slope="+ slope + " ," +"SlopeError="+  slopeError ;
}

}
