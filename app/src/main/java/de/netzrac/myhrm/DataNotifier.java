package de.netzrac.myhrm;

import java.io.IOException;

public interface DataNotifier {
	void readEvent(String s) throws IOException;
}
