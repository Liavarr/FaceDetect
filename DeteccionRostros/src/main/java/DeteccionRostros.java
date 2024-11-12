
import org.bytedeco.javacv.*; 
import org.bytedeco.opencv.opencv_core.*; 
import org.bytedeco.opencv.opencv_objdetect.CascadeClassifier; 
import static org.bytedeco.opencv.global.opencv_core.*; 
import static org.bytedeco.opencv.global.opencv_imgproc.*; 
import static org.bytedeco.opencv.global.opencv_objdetect.*; 

public class DeteccionRostros { 

    public static void main(String[] args) throws FrameGrabber.Exception { 
        // Capturar video desde la webcam
        OpenCVFrameGrabber grabber = new OpenCVFrameGrabber(0); 
        grabber.start(); 
        
        OpenCVFrameConverter.ToMat converter = new OpenCVFrameConverter.ToMat(); 
        CanvasFrame canvas = new CanvasFrame("Detección de Rostros", 
                CanvasFrame.getDefaultGamma() / grabber.getGamma()); 
        canvas.setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE); 
        
        // Cargar el clasificador Haar Cascade para la detección de rostros
        CascadeClassifier faceDetector = new CascadeClassifier("C:\\Program Files\\Java\\FaceDetector\\haarcascade_frontalface_alt.xml"); 
        
        Mat frame = new Mat(); 
        RectVector faces = new RectVector(); 
        
        // Bucle para capturar y procesar frames
        while (canvas.isVisible() && (grabber.grab()) != null) { 
            Frame grabbedFrame = grabber.grab(); 
            frame = converter.convert(grabbedFrame).clone(); 
            
            // Convertir a escala de grises
            Mat grayFrame = new Mat(); 
            cvtColor(frame, grayFrame, COLOR_BGR2GRAY); 
            
            // Detectar rostros
            faceDetector.detectMultiScale(grayFrame, faces); 
            
            // Dibujar rectángulos alrededor de los rostros detectados
            for (int i = 0; i < faces.size(); i++) { 
                Rect face = faces.get(i); 
                rectangle(frame, face, new Scalar(0, 255, 0, 1)); // Dibujar rectángulo verde
            } 
            
            // Mostrar el frame con los rostros detectados
            canvas.showImage(converter.convert(frame)); 
        } 
        
        // Detener el grabber y liberar recursos
        grabber.stop(); 
        canvas.dispose(); 
    } 
}

