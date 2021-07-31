import java.util.Scanner;
import java.io.PrintWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.File;

/**
 * 
 * @author Sanyam Kadd
 * @version 1.1
 * This is BibliographyFactory Class used to read and process a .bib file and create 3 different files with 
 * the correct reference formats IEEE, ACM and NJ.
 *
 */
public class BibliographyFactory {
	static int k=0;
	static int i;
	/**
	 * 
	 * @param sc .bib file to read
	 * @param pw IEEE file 
	 * @param pw1 ACM file
	 * @param pw2 NJ file
	 * This method does two things, first find if a .bib file is valid or not and second creating IEEE, ACM and NJ for recpective
	 * valid .bib file
	 */
	public static void processFilesForValidation( Scanner sc, PrintWriter pw, PrintWriter pw1, PrintWriter pw2) {
		String s;
		String spl[], s2[], auth[];
		String field = null, data = null;
		String author=null, journal=null, title=null, year=null, volume=null, number=null, doi=null, ISSN=null, month=null, pages=null, keywords=null;
		int j=1;
       while(sc.hasNextLine()) {
    	   s=sc.nextLine();
    	   if(s.equals("}")) {
    		   author=author.replace(" and ", ", ");
    		   pw.println(author+". \""+title+"\", "+journal+", vol."+volume+", no."+number+", p."+pages+","+month+" "+year);
    		   pw.println("");
    		   auth=author.split(", ");
    		   pw1.println("["+(j++)+"]  "+auth[0]+" et al. "+year+". "+title+". "+journal+". "+volume+", "+number+" ("+year+"), "+pages+". "+"DOI:https://doi.org/"+doi+". ");
    		   author=author.replace(", ", " & ");
    		   pw1.println("");
    		   pw2.println(author+". "+title+". "+volume+", "+pages+"("+year+")");
    		   pw2.println("");
    		   
           continue;}
    	   else if(s.length()==0)
    		   continue;
    	   else if(s.contains("@"))
    		   {   
    			   while(!(s.contains("=")))
    			   {s=sc.nextLine();
    			   if(s.length()==0)
    				   continue;}
    		   }
    	   while(s.equals(" ")||s.equals("")||s.equals("  ")) {
    		   s=sc.nextLine();
    	   }
    	   spl =s.split("=");
    	   field=spl[0];
    	   s2=spl[1].split("}");
    	   data=s2[0].substring(1);
    	   File f, f1, f2;
    	   try {
    		   checkValidity(data);
    		  }
    	   catch(FileInvalidException e){
    		   k++;
    		   System.out.println(e.getMessage());
    		   System.out.println("======================================================================");
    		   System.out.println("");
    		   System.out.println("Problem detected with input file: Latex"+i+".bib");
    		   System.out.println("File is invalid: Field \""+field+"\" is empty. Processing stopped at this point. Other empty fields may be present a well!");
    		   System.out.println("");
    		   sc.close();
    	       pw.close();
    	       pw1.close();
    	       pw2.close();
    		   f= new File("C:\\Users\\User\\eclipse-workspace\\OOP2A3\\IEEE"+i+".json");
    		   f1=new File("C:\\Users\\User\\eclipse-workspace\\OOP2A3\\ACM"+i+".json");
    		   f2=new File("C:\\Users\\User\\eclipse-workspace\\OOP2A3\\NJ"+i+".json");
               f.delete();
               f1.delete();
               f2.delete();
    		   break;
    		 }
    	   if(field.equals("author"))
    	   { author=data;
    	   }
    	   else if(field.equals("journal"))
    	   { journal=data;
    	   }
    	   else if(field.equals("title"))
    	   { title=data;
    	   }
    	   else if(field.equals("year"))
    	   { year=data;
    	   }
    	   else if(field.equals("volume"))
    		   {volume=data;
    	   }
    	   else if(field.equals("number"))
    	   { number=data;
    	   }
    	   else if(field.equals("doi"))
    	   {  doi=data;
    	   }
    	   else if(field.equals("ISSN"))
    	   {   ISSN=data;
    	   }
    	   else if(field.equals("month"))
    	   { month=data;
    	   }
    	   else if(field.equals("pages"))
    	   {  pages=data;
    	   }
    	   else if(field.equals("keywords"))
    	   { keywords=data;
    	   }
		}
		
	}
	
		/**
		 * @param data String name of data
		 * @return boolean value if data is null or not
		 * @throws FileInvalidException if data is null
		 * This method checks if the data is null or not
		 */
	public static boolean checkValidity(String data) throws FileInvalidException {
		if(data.equals("")) {
			throw new FileInvalidException("Error: Detected Empty Field!");
		 }
		return true;
		
	}
	/**
	 * @param file String name of
	 * @return boolean value if file exists or not
	 * @throws FileNotFoundException if file does not exist
	 */
	public static boolean checkFile(String file) throws FileNotFoundException {
		File f=new File("C:\\Users\\User\\eclipse-workspace\\Project2\\"+file);
		if(f.exists())
			return true;
		throw new FileNotFoundException();
	}
	/**
	 * 
	 * @param b BufferedReader b
	 * @throws IOException
	 * This method display the contents of BufferedReader file in the screen
	 */
	
	public static void displayContents(BufferedReader b) throws IOException{
		String s;
		s=b.readLine();
		System.out.println("Here are the contents of the successfully created json file:");
		System.out.println("");
		System.out.println("");
		while(s!=null) {
			System.out.println(s);
			s=b.readLine();
		}
		b.close();
		
	}

	public static void main(String[] args)   {
		// TODO Auto-generated method stub
		
		System.out.println("Welcome to BiblioGraphy Factory!");
		System.out.println("");
		Scanner sc = null;
		Scanner keyboard=new Scanner(System.in);
		PrintWriter pw = null,pw1=null,pw2=null;
		for( i=1;i<=10;i++) {
		try {
	    sc= new Scanner(new FileInputStream("Latex"+i+".bib"));  
		}
		catch( FileNotFoundException e){
			System.out.println("Could not open Latex"+i+".bib for reading. "
					+ "\nPlease check if file exists. Program will terminate after closing any opened files");
			System.exit(0);
		}
		try {
		 pw = new PrintWriter(new FileOutputStream("IEEE"+i+".json"));
		}
		catch(FileNotFoundException e){
			System.out.println("Could not create file IEEE"+i+".json ");
				System.out.println("Program will terminate");
			System.exit(0);
		}
		
		try {
			 pw1= new PrintWriter(new FileOutputStream("ACM"+i+".json"));
	
			}
		catch(FileNotFoundException e){
				System.out.println("Could not create file ACM"+i+".json ");
				System.out.println("Program will terminate");
				System.exit(0);
		}
		try {
				 pw2 =new PrintWriter(new FileOutputStream("NJ"+i+".json"));
			}
		catch(FileNotFoundException e)
		{
					System.out.println("Could not create file NJ"+i+".json ");
					System.out.println("Program will terminate");
					System.exit(0);
		}
		
		processFilesForValidation(sc, pw, pw1, pw2);
		
		/*String s;
		String spl[], s2[], auth[];
		String field = null, data = null;
		String author=null, journal=null, title=null, year=null, volume=null, number=null, doi=null, ISSN=null, month=null, pages=null, keywords=null;
		int j=1;
       while(sc.hasNextLine()) {
    	   s=sc.nextLine();
    	   if(s.equals("}")) {
    		   author=author.replace(" and ", ", ");
    		   pw.println(author+". \""+title+"\", "+journal+", vol."+volume+", no."+number+", p."+pages+","+month+" "+year);
    		   pw.println("");
    		   auth=author.split(", ");
    		   pw1.println("["+(j++)+"]  "+auth[0]+" et al. "+year+". "+title+". "+journal+". "+volume+", "+number+" ("+year+"), "+pages+". "+"DOI:https://doi.org/"+doi+". ");
    		   author=author.replace(", ", " & ");
    		   pw1.println("");
    		   pw2.println(author+". "+title+". "+volume+", "+pages+"("+year+")");
    		   pw2.println("");
    		   
           continue;}
    	   else if(s.length()==0)
    		   continue;
    	   else if(s.contains("@"))
    		   {   
    			   while(!(s.contains("=")))
    			   {s=sc.nextLine();
    			   if(s.length()==0)
    				   continue;}
    		   }
    	   while(s.equals(" ")||s.equals("")||s.equals("  ")) {
    		   s=sc.nextLine();
    	   }
    	   spl =s.split("=");
    	   field=spl[0];
    	   s2=spl[1].split("}");
    	   data=s2[0].substring(1);
    	   File f, f1, f2;
    	   try {
    		   checkValidity(data);
    		  }
    	   catch(FileInvalidException e){
    		   k++;
    		   System.out.println(e.getMessage());
    		   System.out.println("======================================================================");
    		   System.out.println("");
    		   System.out.println("Problem detected with input file: Latex"+i+".bib");
    		   System.out.println("File is invalid: Field \""+field+"\" is empty. Processing stopped at this point. Other empty fields may be present a well!");
    		   System.out.println("");
    		   sc.close();
    	       pw.close();
    	       pw1.close();
    	       pw2.close();
    		   f= new File("C:\\Users\\User\\eclipse-workspace\\OOP2A3\\IEEE"+i+".json");
    		   f1=new File("C:\\Users\\User\\eclipse-workspace\\OOP2A3\\ACM"+i+".json");
    		   f2=new File("C:\\Users\\User\\eclipse-workspace\\OOP2A3\\NJ"+i+".json");
               f.delete();
               f1.delete();
               f2.delete();
    		   break;
    		 }
    	   if(field.equals("author"))
    	   { author=data;
    	   }
    	   else if(field.equals("journal"))
    	   { journal=data;
    	   }
    	   else if(field.equals("title"))
    	   { title=data;
    	   }
    	   else if(field.equals("year"))
    	   { year=data;
    	   }
    	   else if(field.equals("volume"))
    		   {volume=data;
    	   }
    	   else if(field.equals("number"))
    	   { number=data;
    	   }
    	   else if(field.equals("doi"))
    	   {  doi=data;
    	   }
    	   else if(field.equals("ISSN"))
    	   {   ISSN=data;
    	   }
    	   else if(field.equals("month"))
    	   { month=data;
    	   }
    	   else if(field.equals("pages"))
    	   {  pages=data;
    	   }
    	   else if(field.equals("keywords"))
    	   { keywords=data;
    	   }
		}*/
       sc.close();
       pw.close();
       pw1.close();
       pw2.close();
		}	
		   if(k==0) {
			   System.out.println("No files were invalid. Therefore all 10 valid files have beed created");
		   }
		   else {
			   	System.out.println("A total of "+k+" files were invalid and, could not be processed. All other "+(10-k)+" \"valid\" files have been created.");
		   }
		   System.out.println("");
		   
		   String fi,fl;
		   System.out.print("Please enter the name of the file you need to review: ");
		   fi=keyboard.next();
		   BufferedReader br, br1;
		   try {
		       br= new BufferedReader(new FileReader(fi));
			   checkFile(fi);
			   System.out.println("");
			   displayContents(br);
			   
		   }
		   catch(FileNotFoundException e) {
			   System.out.println("Could not open input file; possibly it could not be created! ");
			   System.out.println("");
			   System.out.println("However, you will be allowed another chance to enter file name");
			   System.out.print("Please enter the name of the file you need to review: ");
			   fl=keyboard.next();
			   try {
			   br1=new BufferedReader(new FileReader(fl));
			   checkFile(fl);
			   System.out.println("");
			   displayContents(br1);
			   }
			   catch(FileNotFoundException ee){
				   System.out.println("Could not open input file again. Either file does not exist or could not be created.");
				   System.out.println("");
				   System.out.println("Sorry! I am unable to display your desired files. Program will exit.");
				   
			   }
			   catch(IOException ee) {
				   System.out.println(ee.getMessage());
		   }
			   
			}
		   catch(IOException e) {
			   System.out.println(e.getMessage());
			   
		   }
		   finally {
			   System.out.println("");
			   System.out.println("Goodbye! Hope you have enjoyed creating the needed files using Bibliography" );
			   
		   }
	   }
		
		}
	
	
	

	


