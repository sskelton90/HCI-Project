package utils;

import java.io.File;

import javax.swing.filechooser.FileFilter;

public class ImageFileFilter extends FileFilter {

	private final String TIFF = "tiff";
	private final String TIF = "tif";
	private final String JPEG = "jpeg";
	private final String JPG = "jpg";
	private final String GIF = "gif";
	private final String PNG = "png";
	
	@Override
	public boolean accept(File f) {
		if (f.isDirectory()) {
	        return true;
	    }

	    String extension = getExtension(f);
	    if (extension != null) {
	        if (extension.equals(TIFF) ||
	            extension.equals(TIF) ||
	            extension.equals(GIF) ||
	            extension.equals(JPEG) ||
	            extension.equals(JPG) ||
	            extension.equals(PNG)) {
	                return true;
	        } else {
	            return false;
	        }
	    }

	    return false;
	}

	@Override
	public String getDescription() {
		return "Images";
	}
	
    public String getExtension(File f) {
        String ext = null;
        String s = f.getName();
        int i = s.lastIndexOf('.');

        if (i > 0 &&  i < s.length() - 1) {
            ext = s.substring(i+1).toLowerCase();
        }
        return ext;
    }

}
