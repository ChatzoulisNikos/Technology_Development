 package engine;

import java.io.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;

import org.apache.commons.math3.util.Pair;


import dom2app.IMeasurementVector;
import dom2app.ISingleMeasureRequest;
import dom2app.SingleMeasureRequest;
import dom2app.MeasurementVector;



public class MainController implements IMainController {
	private List<IMeasurementVector> measurements = new ArrayList<>();
	private List<ISingleMeasureRequest> listOfrequests=new ArrayList<>();
	//private List<ISingleMeasureRequest> retrievedRequests=new ArrayList<>();
	private Set<String> nameOfRequests = new HashSet<String>();
	

	@Override
	public List<IMeasurementVector> load(String fileName, String delimiter) throws FileNotFoundException, IOException {
		// TODO Auto-generated method stub
		try(BufferedReader br = new BufferedReader(new FileReader(fileName)))
		{
			String headerLine=br.readLine();
			String[] header=headerLine.split(delimiter);
			String line;
			while((line=br.readLine())!= null) {
				//System.out.println(line);
				String[] parts =line.split(delimiter);
				
				try {
					String objectid=parts[0];
					//System.out.println(objectid);
					String countryname=parts[1];
					//System.out.println(countryname);
					String ISO2=parts[2];
					//System.out.println(ISO2);
					String ISO3=parts[3];
					//System.out.println(ISO3);
					String disaster=parts[4];
					//System.out.println(disaster);
					
					List<Pair<Integer, Integer>> pairedValues=takePair(header ,parts);
					System.out.println(objectid);
					System.out.println(pairedValues);
					
					IMeasurementVector measurement= new MeasurementVector(objectid, countryname, ISO2, ISO3, disaster, pairedValues);
					measurements.add(measurement);
				}catch(Exception e) {
					System.out.println(e);
				}
			}
			
			}catch(IOException e) {
				e.printStackTrace();
	}
	
	return measurements;
	}
	
	

	@Override
	public ISingleMeasureRequest findSingleCountryIndicator(String requestName, String countryName,
			String indicatorString) throws IllegalArgumentException {
		ISingleMeasureRequest result=null;
		
		try{
			// TODO Auto-generated method stub
		String reqName  = requestName;
		for(int i =0; i<measurements.size(); i++) {
			IMeasurementVector req = measurements.get(i);
			if(req.getCountryName().equals(countryName) && req.getIndicatorString().equals(indicatorString)){
				
				result = new SingleMeasureRequest( reqName, req.getObjectId(), req.getCountryName(), req.getISO2(), req.getISO3(), req.getIndicatorString(), req.getMeasurements());
				listOfrequests.add(result);
				nameOfRequests.add(reqName);
			}
		}
		}catch(Exception e) {
			e.printStackTrace();
		}		
		return result;
	}

	@Override
	public ISingleMeasureRequest findSingleCountryIndicatorYearRange(String requestName, String countryName,
			String indicatorString, int startYear, int endYear) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		
		ISingleMeasureRequest result=null;
		List<Pair<Integer,Integer>> yearRangeMeasurements= new ArrayList<>();
		try {
			String reqName=requestName;
			for(int i=0; i<measurements.size(); i++) {
				IMeasurementVector req =measurements.get(i);
				List<Pair<Integer,Integer>> pairs =req.getMeasurements();
				for(Pair<Integer,Integer> k : pairs) {
					int year=k.getFirst();
					
					
					if(req.getCountryName().equals(countryName) && (year>=startYear && year<=endYear) && req.getIndicatorString().equals(indicatorString)) {
						int numberOfDisasters=k.getSecond();
						yearRangeMeasurements.add(new Pair<>(year, numberOfDisasters));
						
						result = new SingleMeasureRequest(reqName, req.getObjectId(), req.getCountryName(), req.getISO2(), req.getISO3(), req.getIndicatorString(), yearRangeMeasurements);
						listOfrequests.add(result);
						nameOfRequests.add(reqName);
						
				}
				}
			
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}

	@Override
	public Set<String> getAllRequestNames() {
		// TODO Auto-generated method stub
		return nameOfRequests;
	}

	@Override
	public ISingleMeasureRequest getRequestByName(String requestName) {
		// TODO Auto-generated method stub
		try {
			String reqName=requestName;
			for(ISingleMeasureRequest request : listOfrequests) {
				if(request.getRequestName().equals(reqName)) {
					//retrievedRequests.add(request);
					return request;
				}
			}
			
			
		}catch(Exception e) {
			e.getStackTrace();
		}
		return null;
	}

	@Override
	public ISingleMeasureRequest getRegression(String requestName) {
		// TODO Auto-generated method stub
		try {
			String reqName=requestName;
			getRequestByName(reqName);
			for(int i=0; i<listOfrequests.size(); i++) {
				ISingleMeasureRequest request=listOfrequests.get(i);
				if(request.getRequestName().equals(reqName)) {
					return request;
				}
			}
			
		}catch(Exception e) {
			e.getStackTrace();
		}
		return null;
	}

	@Override
	public ISingleMeasureRequest getDescriptiveStats(String requestName) {
		// TODO Auto-generated method stub
		try {
			String reqName=requestName;
			getRequestByName(reqName);
			for(int i=0; i<listOfrequests.size(); i++) {
				ISingleMeasureRequest request= listOfrequests.get(i);
				if(request.getRequestName().equals(reqName)) {
					return request;
				}
			}
			
		}catch(Exception e) {
			e.getStackTrace();
		}
		return null;
	}

	@Override
	public int reportToFile(String outputFilePath, String requestName, String reportType) throws IOException {
		// TODO Auto-generated method stub
		String reqName = requestName;
		ISingleMeasureRequest request = getRequestByName(reqName);
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFilePath, true))) {
			if(reportType.equals("text")) {
				writeTxTReport(writer, request);
			}
		else if(reportType.equals("html")) {
			writeHtmlReport(writer, request);
		}
			else {
				writeMdReport(writer,request);
			}
		}
		return 0;
			
	}
	
	private void writeTxTReport(BufferedWriter writer, ISingleMeasureRequest request) throws IOException {
		
		String nameOfCo = request.getRequestFilter();
		String[] nameCoIn = nameOfCo.split(" ");
		
		writer.write("Request Name: " + request.getRequestName());
		writer.newLine();
		writer.write("Country Name: " + nameCoIn[0]);
		writer.newLine();
		writer.write("Disaster Type: " + nameCoIn[1]);
		writer.newLine();
		writer.write("Measurement List: " + request.getAnswer().getMeasurements());
		writer.newLine();
		writer.write("Basic Statistics: " + request.getDescriptiveStatsString());
        writer.newLine();
        writer.write("Regression Result: " + request.getRegressionResultString());
        writer.newLine();

	}
	 private void writeMdReport(BufferedWriter writer, ISingleMeasureRequest request) throws IOException {
		 String nameOfCo = request.getRequestFilter();
		 String[] nameCoIn=nameOfCo.split(" ");
		 List<Pair<Integer,Integer>> pairs = request.getAnswer().getMeasurements();
		 writer.write("**Request Name: " + request.getRequestName() + "**");
		 writer.newLine();
		 writer.newLine();
		 writer.write("*Country Name: " + nameCoIn[0] + "*");
		 writer.newLine();
		 writer.newLine();
		 writer.write("*Type of disaster: " + nameCoIn[1] + "*");
		 writer.newLine();
		 writer.newLine();
		 writer.write("|Year|Number of disaster|");
		 writer.newLine();
		 writer.write("|:-----|:------------------|");
		 writer.newLine();
		 for(Pair<Integer,Integer> k : pairs) {
			 int year=k.getFirst();
			 int numberOfDisaster=k.getSecond();
			 writer.write("|" + year + "| " + numberOfDisaster + "|");
			 writer.newLine();
		 }
		 writer.newLine();
		 writer.write("Descriptice stats: " + request.getDescriptiveStatsString());
		 writer.newLine();
		 writer.newLine();
		 writer.write("Regression results: " + request.getRegressionResultString());
		 writer.newLine();
		 
		
	}
	private void writeHtmlReport(BufferedWriter writer, ISingleMeasureRequest request) throws IOException {
	    // Write HTML-formatted report
		String nameOfCo = request.getRequestFilter();
		String[] nameCoIn = nameOfCo.split(" ");
		List<Pair<Integer, Integer>> pairs = request.getAnswer().getMeasurements();
	    writer.write("<html>");
	    writer.newLine();
	    writer.write("<head>");
	    writer.newLine();
	    writer.write("<style>");
	    writer.newLine();
	    writer.write("table,th,td { border:1px solid black; }");
	    writer.newLine();
	    writer.write("body { font-family: 'Arial', sans-serif; }");
	    writer.newLine();
	    // Add other styling rules as needed
	    writer.write("</style>");
	    writer.newLine();
	    writer.write("</head>");
	    writer.newLine();
	    writer.write("<body>");
	    writer.newLine();
	    writer.write("<b>Request Name: " + request.getRequestName() + "</b>");
	    writer.newLine();
	    writer.newLine();
	    writer.write("<i>Country Name: " + nameCoIn[0] + "</i>");
	    writer.newLine();
	    writer.write("<i>Disaster Type: " + nameCoIn[1] + "</i>");
	    writer.newLine();
	    writer.write("<table>");
	    writer.newLine();
	    writer.write("	<tr>");
	    writer.newLine();
	    writer.write("		<th>" + "Year" + "</th>");
	    writer.newLine();
		writer.write("		<th>" + "Number Of Disasters" + "</th>");
		writer.newLine();
		writer.write("	</tr>");
		writer.newLine();
	    for (Pair<Integer, Integer> k : pairs) {
			int year = k.getFirst();
			int numberOfDisaster = k.getSecond();
			writer.write("<tr>");
			writer.newLine();
			writer.write("<td>" + year + "</td>");
			writer.newLine();
			writer.write("<td>" + numberOfDisaster + "</td");
			writer.newLine();
			writer.write("</tr>");
			writer.newLine();
	    }
	    writer.write("<p>Basic Statistics: " + request.getDescriptiveStatsString() + "</p>");
	    writer.newLine();
	    writer.write("<p>Regression Result: "+ request.getRegressionResultString() + "</p>");
	    // Add other information with appropriate HTML tags
	    writer.write("</body>");
	    writer.newLine();
	    writer.write("</html>");
	}
	
	public List<Pair<Integer , Integer>> takePair(String[] header ,String[] parts){
		List<Pair<Integer, Integer>> values =new ArrayList<>();
			for(int i=5; i<header.length && i<parts.length; i++) {
				System.out.println(i);
				
				String years=header[i];
				String disasters=parts[i];
			
				if(!years.isEmpty() && !disasters.isEmpty()) {
					int year=Integer.parseInt(years);
					int numberOfDisasters=Integer.parseInt(disasters);
					values.add(new Pair<>(year,numberOfDisasters));
				}
				else {
					int year = Integer.parseInt(years);
					values.add(new Pair<>(year, 0));
					
			
			}
			}
		
		
		
		return values;
		
	}

	

}
	
