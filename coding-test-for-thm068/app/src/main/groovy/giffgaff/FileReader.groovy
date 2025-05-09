package giffgaff

import giffgaff.exceptions.ResourceNotFoundException

class FileReader {
    private String fileName

    FileReader(String fileName) {
        this.fileName = fileName
    }

    String getContent() {
        try {
            URL resource = getClass().getClassLoader().getResource(fileName)
            File file = new File(resource.toURI())
            return file.text
        }
        catch (NullPointerException | FileNotFoundException ex) {
            throw new ResourceNotFoundException("The file : ${fileName} does not exist")
        }
        catch (Exception ex ) {
            throw ex
        }

    }


}
