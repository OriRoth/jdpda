package roth.ori.jdpda;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

public class GenerateAll {
	public static final String generatedOutputPath = System.getProperty("user.dir")
			+ "/source/test/java/roth/ori/jdpda/generated/";

	public static final Map<String, String> files = new HashMap<>();
	public static final Object[][] APIClasses = { //
			{ "LAPlusBBAPI", LAPlusBB.M }, //
			{ "LAStarAPI", LAStar.M }, //
			{ "LAStarBAPI", LAStarB.M }, //
			{ "LBalancedParenthesesAPI", LBalancedParentheses.M }, //
			{ "LExtendedBalancedParenthesesAPI", LExtendedBalancedParentheses.M }, //
			{ "LLispParenthesesAPI", LLispParentheses.M }, //
	};
	static {
		for (Object[] APIClass : APIClasses)
			files.put((String) APIClass[0], "package roth.ori.jdpda.generated;"
					+ new DPDA2JavaFluentAPIEncoder<>((String) APIClass[0], (DPDA<?, ?, ?>) APIClass[1]).encoding);
	}

	@Test
	public void generateAll() throws IOException {
		System.out.println("Current output directory is " + generatedOutputPath + ".");
		Path directoryPath = Paths.get("./source/test/java/roth/ori/jdpda/generated/");
		if (!Files.exists(directoryPath)) {
			Files.createDirectory(directoryPath);
			System.out.println("Directory " + directoryPath + " created successfully.");
		}
		for (String fileName : files.keySet()) {
			Files.write(
					Paths.get("./source/test/java/roth/ori/jdpda/generated/" + fileName + ".java"),
					Collections.singleton(files.get(fileName)), StandardOpenOption.CREATE, StandardOpenOption.WRITE);
			System.out.println("File " + fileName + ".java written successfully.");
		}
	}
}
