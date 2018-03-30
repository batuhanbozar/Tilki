package kasirgalabs.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.fail;

import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.junit.Test;

import kasirgalabs.Exam;
import kasirgalabs.FoxClientUtilities;

public class TestFoxClientUtilities {
	FoxClientUtilities foxClientUtilities = new FoxClientUtilities();
	@Test
	public void checkInForNull() {
		try {
			foxClientUtilities.checkIn(null, "surname", "id", "exam");
			foxClientUtilities.checkIn("name", null, "id", "exam");
			foxClientUtilities.checkIn("name", "surname", null, "exam");
			foxClientUtilities.checkIn("name", "surname", "id", null);
			fail();
		} catch (Exception e) {
			assert(true);
		}
	}
	@Test
	public void checkInForDummy() {
			try {
				int result = foxClientUtilities.checkIn("name", "surname", "id", "exam");
				assertEquals(result, -1);
			} catch (IOException e) {
				
			}
	}
	@Test
	public void verifyInstructorKeyForNull() {
		try {
			int result =foxClientUtilities.verifyInstructorKey(null, "surname", "id", "exam", "instructorKey");
			assertEquals(result, -1);
			result =foxClientUtilities.verifyInstructorKey("name", null, "id", "exam", "instructorKey");
			assertEquals(result, -1);
			result =foxClientUtilities.verifyInstructorKey("name", "surname", null, "exam", "instructorKey");
			assertEquals(result, -1);
			result =foxClientUtilities.verifyInstructorKey("name", "surname", "id", null, "instructorKey");
			assertEquals(result, -1);
			result =foxClientUtilities.verifyInstructorKey("name", "surname", "id", "exam", null);
			assertEquals(result, -1);
		} catch (Exception e) {
			assert(true);
		}
	}
	
	@Test
	public void verifyInstructorKeyForDummy() {
			try {
				int result = foxClientUtilities.verifyInstructorKey("name", "surname", "id", "exam", "instructorKey");
				assertEquals(result, -1);
			} catch (IOException e) {
				
			}
	}
	
	
	@Test
	 public void verifyInstructorKeyTest(){
		     // Connect to the host.
	        String dataOutputString = "Key verify";
	        DataInputStream in = testHost(dataOutputString);
	        
			try {
				 String status = in.readUTF(); // Read the status code.
			        if(status != null) {
			            char c = status.charAt(0);
			            assertEquals(Character.getNumericValue(c),'1');
			        }
			} catch (IOException e) {
				e.printStackTrace();
			} // Read the status code.
	        
	    }
	
	
	
	 @Test
		public void availableExamsGettingTest() {
			Exam[] examList;
			try {
				examList = foxClientUtilities.availableExams();
				assertNotEquals(examList.length, 0);
			} catch (Exception e) {

			}
		}
	
	@Test
	public void createZipFileTest() {
		int numberOfFile = 3;
		File[] testFiles = createTestFileList(numberOfFile);
		try {
			String zipFileName = foxClientUtilities.createZipFile(testFiles);
			if(new File(zipFileName).exists()) // File exists
		    {
				File f = new File(zipFileName);
				assert(f.exists());
				f.delete();
				for (int i = 0; i < numberOfFile; i++) {
					f = new File("testFile"+Integer.toString(i)+".txt");
					f.delete();
				}
				 
		    }
		} catch (Exception e) {
		}
	}
	
	public File[] createTestFileList(int numberOfFile) {
		File[] testFiles = new File[numberOfFile];
		for (int i = 0; i < numberOfFile; i++) {
			testFiles[i]= createTestFile("testFile"+Integer.toString(i)+".txt");
		}
		return testFiles;
	}
	
	public File createTestFile(String fileName) {
		BufferedWriter writer = null;
		File testFile = new File(fileName);
		 try {
			writer = new BufferedWriter(new FileWriter(testFile));
			 writer.write("This is a sample text.");
			 writer.close();
		} catch (Exception e) {

		} finally {
            try {
                writer.close();
            } catch (Exception e) {
            }
        }
		return testFile;
	}
	
	///////////////////////////////////////////////
	private DataInputStream testHost(String out){
		if (out.equals("Key verify")){
			return new DataInputStream(getInputStream("1x1xxxxxxxxxxx"));
		}
		return null;
	}
	
	private InputStream getInputStream(String content){
		InputStream stream = new ByteArrayInputStream(content.getBytes(StandardCharsets.UTF_8));
		return stream;
	}
	

	///////////////////////////////////////////////
}