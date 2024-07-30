public class FactoryMethodPattern {
    public static void main(String[] args) {
        DocumentFactory wordFactory = new WordDocFactory();
        Document wordDoc = wordFactory.createDocument();
        wordDoc.open();
        wordDoc.save();
        wordDoc.close();

        DocumentFactory pdfFactory = new PdfDocumentFactory();
        Document pdfDoc = pdfFactory.createDocument();
        pdfDoc.open();
        pdfDoc.save();
        pdfDoc.close();

        DocumentFactory excelFactory = new ExcelDocumentFactory();
        Document excelDoc = excelFactory.createDocument();
        excelDoc.open();
        excelDoc.save();
        excelDoc.close();
    }

    // Document interface
    public interface Document {
        void open();
        void save();
        void close();
    }

    // WordDocument class
    public static class WordDocument implements Document {
        @Override
        public void open() {
            System.out.println("Word Document opening");
        }

        @Override
        public void save() {
            System.out.println("Word Document saving");
        }

        @Override
        public void close() {
            System.out.println("Word Document closing");
        }
    }

    // PdfDocument class
    public static class PdfDocument implements Document {
        @Override
        public void open() {
            System.out.println("PDF Document opening");
        }

        @Override
        public void save() {
            System.out.println("PDF Document saving");
        }

        @Override
        public void close() {
            System.out.println("PDF Document closing");
        }
    }

    // ExcelDocument class
    public static class ExcelDocument implements Document {
        @Override
        public void open() {
            System.out.println("Excel Document opening");
        }

        @Override
        public void save() {
            System.out.println("Excel Document saving");
        }

        @Override
        public void close() {
            System.out.println("Excel Document closing");
        }
    }

    // DocumentFactory abstract class
    public static abstract class DocumentFactory {
        public abstract Document createDocument();
    }

    // WordDocFactory class
    public static class WordDocFactory extends DocumentFactory {
        @Override
        public Document createDocument() {
            return new WordDocument();
        }
    }

    // PdfDocumentFactory class
    public static class PdfDocumentFactory extends DocumentFactory {
        @Override
        public Document createDocument() {
            return new PdfDocument();
        }
    }

    // ExcelDocumentFactory class
    public static class ExcelDocumentFactory extends DocumentFactory {
        @Override
        public Document createDocument() {
            return new ExcelDocument();
        }
    }
}
