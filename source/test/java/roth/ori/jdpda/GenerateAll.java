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
	private static final String PATH = "./source/test/java/roth/ori/jdpda/generated/";

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
			files.put((String) APIClass[0], "package roth.ori.jdpda.generated;\n\n"
					+ new DPDA2JavaFluentAPIEncoder<>((String) APIClass[0], (DPDA<?, ?, ?>) APIClass[1]).encoding);
	}

	@Test
	public void generateAll() throws IOException {
		System.out.println("Current output directory is " + PATH + ".");
		Path directoryPath = Paths.get(PATH);
		if (!Files.exists(directoryPath)) {
			Files.createDirectory(directoryPath);
			System.out.println("Directory " + directoryPath + " created successfully.");
		}
		for (String fileName : files.keySet()) {
			Path filePath = Paths.get(PATH + fileName + ".java");
			if (Files.exists(filePath))
				Files.delete(filePath);
			Files.write(filePath, Collections.singleton(files.get(fileName)), StandardOpenOption.CREATE,
					StandardOpenOption.WRITE);
			System.out.println("File " + fileName + ".java written successfully.");
		}
	}
}
