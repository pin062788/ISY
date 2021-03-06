package top.zbeboy.isy.web.util;

/**
 * Created by zbeboy on 2017/1/20.
 */

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;
import java.util.Iterator;

@Slf4j
public class ImageUtils {

    /**
     * 获取图片尺寸信息
     *
     * @param filePath a {@link java.lang.String} object.
     * @return [width, height]
     */
    public static int[] getSizeInfo(String filePath) throws Exception {
        File file = new File(filePath);
        return getSizeInfo(file);
    }

    /**
     * 获取图片尺寸信息
     *
     * @param url a {@link java.net.URL} object.
     * @return [width, height]
     */
    public static int[] getSizeInfo(URL url) throws Exception {
        InputStream input = null;
        try {
            input = url.openStream();
            return getSizeInfo(input);
        } catch (IOException e) {
            log.error("Get file size error : {}", e);
            throw new Exception(e);
        }
    }

    /**
     * 获取图片尺寸信息
     *
     * @param file a {@link java.io.File} object.
     * @return [width, height]
     */
    public static int[] getSizeInfo(File file) throws Exception {
        if (!file.exists()) {
            throw new Exception("file " + file.getAbsolutePath() + " doesn't exist.");
        }
        BufferedInputStream input = null;
        try {
            input = new BufferedInputStream(new FileInputStream(file));
            return getSizeInfo(input);
        } catch (FileNotFoundException e) {
            log.error("Get file size error : {}", e);
            throw new Exception(e);
        }
    }

    /**
     * 获取图片尺寸
     *
     * @param input a {@link java.io.InputStream} object.
     * @return [width, height]
     */
    public static int[] getSizeInfo(InputStream input) throws Exception {
        try {
            BufferedImage img = ImageIO.read(input);
            int w = img.getWidth(null);
            int h = img.getHeight(null);
            return new int[]{w, h};
        } catch (IOException e) {
            log.error("Get file size error : {}", e);
            throw new Exception(e);
        }
    }

    /**
     * 重调图片尺寸
     *
     * @param srcFilePath 原图路径
     * @param destFile    目标文件
     * @param width       新的宽度，小于1则忽略，按原图比例缩放
     * @param height      新的高度，小于1则忽略，按原图比例缩放
     */
    public static void resize(String srcFilePath, String destFile, int width, int height) throws Exception {
        resize(srcFilePath, destFile, width, height, -1, -1);
    }

    /**
     * 重调图片尺寸
     *
     * @param input  a {@link java.io.InputStream} object.
     * @param output a {@link java.io.OutputStream} object.
     * @param width  a int.
     * @param height a int.
     */
    public static void resize(InputStream input, OutputStream output, int width, int height) throws Exception {
        resize(input, output, width, height, -1, -1);
    }

    /**
     * 重调图片尺寸
     *
     * @param input     a {@link java.io.InputStream} object.
     * @param output    a {@link java.io.OutputStream} object.
     * @param width     a int.
     * @param height    a int.
     * @param maxWidth  a int.
     * @param maxHeight a int.
     */
    public static void resize(InputStream input, OutputStream output,
                              int width, int height, int maxWidth, int maxHeight) throws Exception {

        if (width < 1 && height < 1 && maxWidth < 1 && maxHeight < 1) {
            try {
                IOUtils.copy(input, output);
            } catch (IOException e) {
                throw new Exception("resize error: ", e);
            }
        }
        try {
            BufferedImage img = ImageIO.read(input);
            boolean hasNotAlpha = !img.getColorModel().hasAlpha();
            double w = img.getWidth(null);
            double h = img.getHeight(null);
            int toWidth;
            int toHeight;
            double rate = w / h;

            if (width > 0 && height > 0) {
                rate = ((double) width) / ((double) height);
                toWidth = width;
                toHeight = height;
            } else if (width > 0) {
                toWidth = width;
                toHeight = (int) (toWidth / rate);
            } else if (height > 0) {
                toHeight = height;
                toWidth = (int) (toHeight * rate);
            } else {
                toWidth = ((Number) w).intValue();
                toHeight = ((Number) h).intValue();
            }

            if (maxWidth > 0 && toWidth > maxWidth) {
                toWidth = maxWidth;
                toHeight = (int) (toWidth / rate);
            }
            if (maxHeight > 0 && toHeight > maxHeight) {
                toHeight = maxHeight;
                toWidth = (int) (toHeight * rate);
            }

            BufferedImage tag = new BufferedImage(toWidth, toHeight, hasNotAlpha ? BufferedImage.TYPE_INT_RGB : BufferedImage.TYPE_INT_ARGB);

            // Image.SCALE_SMOOTH 的缩略算法 生成缩略图片的平滑度的 优先级比速度高 生成的图片质量比较好 但速度慢
            tag.getGraphics().drawImage(img.getScaledInstance(toWidth, toHeight, Image.SCALE_SMOOTH), 0, 0, null);
            ImageIO.write(tag, hasNotAlpha ? "jpg" : "png", output);
        } catch (Exception e) {
            log.error("Resize error : {}", e);
            throw new Exception(e);
        }

    }

    /**
     * 重调图片尺寸
     *
     * @param srcFile   原图路径
     * @param destFile  目标文件
     * @param width     新的宽度，小于1则忽略，按原图比例缩放
     * @param height    新的高度，小于1则忽略，按原图比例缩放
     * @param maxWidth  最大宽度，限制目标图片宽度，小于1则忽略此设置
     * @param maxHeight 最大高度，限制目标图片高度，小于1则忽略此设置
     */
    public static void resize(String srcFile, String destFile, int width,
                              int height, int maxWidth, int maxHeight) throws Exception {
        resize(new File(srcFile), new File(destFile), width, height, maxWidth, maxHeight);
    }

    /**
     * 重调图片尺寸
     *
     * @param srcFile  原图路径
     * @param destFile 目标文件
     * @param width    新的宽度，小于1则忽略，按原图比例缩放
     * @param height   新的高度，小于1则忽略，按原图比例缩放
     */
    public static void resize(File srcFile, File destFile, int width, int height) throws Exception {
        resize(srcFile, destFile, width, height, -1, -1);
    }

    /**
     * 重调图片尺寸
     *
     * @param srcFile   原图路径
     * @param destFile  目标文件
     * @param width     新的宽度，小于1则忽略，按原图比例缩放
     * @param height    新的高度，小于1则忽略，按原图比例缩放
     * @param maxWidth  最大宽度，限制目标图片宽度，小于1则忽略此设置
     * @param maxHeight 最大高度，限制目标图片高度，小于1则忽略此设置
     */
    public static void resize(File srcFile, File destFile, int width,
                              int height, int maxWidth, int maxHeight) throws Exception {
        if (destFile.exists()) {
            destFile.delete();
        } else {
            destFile.getParentFile().mkdirs();
        }
        InputStream input = null;
        OutputStream output = null;
        try {
            input = new BufferedInputStream(new FileInputStream(srcFile));
            output = new FileOutputStream(destFile);
            resize(input, output, width, height, maxWidth, maxHeight);
        } catch (FileNotFoundException e) {
            log.error("Resize error : {}", e);
            throw new Exception(e);
        }
    }

    /**
     * 裁剪图片
     *
     * @param source a {@link java.lang.String} object.
     * @param target a {@link java.lang.String} object.
     * @param x      a int.
     * @param y      a int.
     * @param w      a int.
     * @param h      a int.
     */
    public static void crop(String source, String target, int x, int y, int w, int h) throws Exception {
        crop(new File(source), new File(target), x, y, w, h);
    }

    /**
     * 裁剪图片
     *
     * @param source a {@link java.io.File} object.
     * @param target a {@link java.io.File} object.
     * @param x      a int.
     * @param y      a int.
     * @param w      a int.
     * @param h      a int.
     */
    public static void crop(File source, File target, int x, int y, int w, int h) throws Exception {
        OutputStream output = null;
        InputStream input = null;
        String ext = FilenameUtils.getExtension(target.getName());
        try {
            input = new BufferedInputStream(new FileInputStream(source));
            if (target.exists()) {
                target.delete();
            } else {
                target.getParentFile().mkdirs();
            }
            output = new BufferedOutputStream(new FileOutputStream(target));
        } catch (IOException e) {
            log.error("Crop error : {}", e);
            throw new Exception(e);
        }
        crop(input, output, x, y, w, h, StringUtils.equalsIgnoreCase("png", ext));
    }

    /**
     * 裁剪图片
     *
     * @param x      a int.
     * @param y      a int.
     * @param w      a int.
     * @param h      a int.
     * @param input  a {@link java.io.InputStream} object.
     * @param output a {@link java.io.OutputStream} object.
     * @param isPNG  a boolean.
     */
    public static void crop(InputStream input, OutputStream output, int x,
                            int y, int w, int h, boolean isPNG) throws Exception {
        try {
            BufferedImage srcImg = ImageIO.read(input);
            int tmpWidth = srcImg.getWidth();
            int tmpHeight = srcImg.getHeight();
            int xx = Math.min(tmpWidth - 1, x);
            int yy = Math.min(tmpHeight - 1, y);

            int ww = w;
            if (xx + w > tmpWidth) {
                ww = Math.max(1, tmpWidth - xx);
            }
            int hh = h;
            if (yy + h > tmpHeight) {
                hh = Math.max(1, tmpHeight - yy);
            }

            BufferedImage dest = srcImg.getSubimage(xx, yy, ww, hh);

            BufferedImage tag = new BufferedImage(w, h, isPNG ? BufferedImage.TYPE_INT_ARGB : BufferedImage.TYPE_INT_RGB);

            tag.getGraphics().drawImage(dest, 0, 0, null);
            ImageIO.write(tag, isPNG ? "png" : "jpg", output);
        } catch (Exception e) {
            log.error("Crop error : {}", e);
            throw new Exception(e);
        }
    }

    /**
     * 压缩图片,PNG图片按JPG处理
     *
     * @param input   a {@link java.io.InputStream} object.
     * @param output  a {@link java.io.OutputStream} object.
     * @param quality 图片质量0-1之间
     */
    public static final void optimize(InputStream input, OutputStream output, float quality) throws Exception {

        // create a BufferedImage as the result of decoding the supplied
        // InputStream
        BufferedImage image;
        ImageOutputStream ios = null;
        ImageWriter writer = null;
        try {
            image = ImageIO.read(input);

            // get all image writers for JPG format
            Iterator<ImageWriter> writers = ImageIO.getImageWritersByFormatName("jpeg");

            if (!writers.hasNext())
                throw new IllegalStateException("No writers found");

            writer = (ImageWriter) writers.next();
            ios = ImageIO.createImageOutputStream(output);

            writer.setOutput(ios);

            ImageWriteParam param = writer.getDefaultWriteParam();

            // optimize to a given quality
            param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
            param.setCompressionQuality(quality);

            // appends a complete image stream containing a single image and
            // associated stream and image metadata and thumbnails to the output
            writer.write(null, new IIOImage(image, null, null), param);
        } catch (IOException e) {
            log.error("Optimize error : {}", e);
        } finally {
            if (ios != null) {
                try {
                    ios.close();
                } catch (IOException e) {
                    log.error("ImageOutputStream closed error : {}", e);
                }
            }
            if (writer != null) {
                writer.dispose();
            }
        }
    }

    /**
     * 压缩图片
     *
     * @param source  a {@link java.lang.String} object.
     * @param target  a {@link java.lang.String} object.
     * @param quality a float.
     */
    public static final void optimize(String source, String target, float quality) throws Exception {
        File fromFile = new File(source);
        File toFile = new File(target);
        optimize(fromFile, toFile, quality);
    }

    /**
     * 压缩图片
     *
     * @param source  a {@link java.io.File} object.
     * @param target  a {@link java.io.File} object.
     * @param quality 图片质量0-1之间
     */
    public static final void optimize(File source, File target, float quality) throws Exception {
        if (target.exists()) {
            target.delete();
        } else {
            target.getParentFile().mkdirs();
        }
        InputStream is = null;
        OutputStream os = null;
        try {
            is = new BufferedInputStream(new FileInputStream(source));
            os = new BufferedOutputStream(new FileOutputStream(target));
            optimize(is, os, quality);
        } catch (FileNotFoundException e) {
            log.error("Optimize error : {}", e);
            throw new Exception(e);
        }
    }

    /**
     * 制作圆角
     *
     * @param srcFile      原文件
     * @param destFile     目标文件
     * @param cornerRadius 角度
     */
    public static void makeRoundedCorner(File srcFile, File destFile, int cornerRadius) throws Exception {
        InputStream in = null;
        OutputStream out = null;

        try {
            in = new BufferedInputStream(new FileInputStream(srcFile));
            destFile.getParentFile().mkdirs();
            out = new BufferedOutputStream(new FileOutputStream(destFile));
            makeRoundedCorner(in, out, cornerRadius);
        } catch (IOException e) {
            log.error("MakeRoundedCorner error : {}", e);
            throw new Exception(e);
        }

    }

    /**
     * 制作圆角
     *
     * @param srcFile      原文件
     * @param destFile     目标文件
     * @param cornerRadius 角度
     */
    public static void makeRoundedCorner(String srcFile, String destFile, int cornerRadius) throws Exception {
        makeRoundedCorner(new File(srcFile), new File(destFile), cornerRadius);
    }

    /**
     * 制作圆角
     *
     * @param inputStream  原图输入流
     * @param outputStream 目标输出流
     * @param radius       角度
     */
    public static void makeRoundedCorner(final InputStream inputStream,
                                         final OutputStream outputStream, final int radius) throws Exception {
        BufferedImage sourceImage = null;
        BufferedImage targetImage = null;
        try {
            sourceImage = ImageIO.read(inputStream);
            int w = sourceImage.getWidth();
            int h = sourceImage.getHeight();
            System.out.println(w);

            int cornerRadius = radius < 1 ? w / 4 : radius;

            targetImage = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);

            Graphics2D g2 = targetImage.createGraphics();

            // This is what we want, but it only does hard-clipping, i.e.
            // aliasing
            // g2.setClip(new RoundRectangle2D ...)

            // so instead fake soft-clipping by first drawing the desired clip
            // shape
            // in fully opaque white with antialiasing enabled...
            g2.setComposite(AlphaComposite.Src);
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(Color.WHITE);
            g2.fill(new RoundRectangle2D.Float(0, 0, w, h, cornerRadius, cornerRadius));

            // ... then compositing the image on top,
            // using the white shape from above as alpha source
            g2.setComposite(AlphaComposite.SrcAtop);
            g2.drawImage(sourceImage, 0, 0, null);
            g2.dispose();
            ImageIO.write(targetImage, "png", outputStream);
        } catch (IOException e) {
            log.error("MakeRoundedCorner error : {}", e);
            throw new Exception(e);
        }
    }

    /**
     * 图片旋转
     *
     * @param srcFile  源文文件
     * @param destFile 输出文件
     * @param angle    rotate
     * @throws Exception 异常
     */
    public static void makeRotate(File srcFile, File destFile, int angle) throws Exception {
        InputStream in = null;
        OutputStream out = null;

        try {
            in = new BufferedInputStream(new FileInputStream(srcFile));
            destFile.getParentFile().mkdirs();
            out = new BufferedOutputStream(new FileOutputStream(destFile));
            makeRotate(in, out, angle);
        } catch (IOException e) {
            log.error("makeRotate error : {}", e);
            throw new Exception(e);
        }

    }

    /**
     * 图片旋转
     *
     * @param srcFile  源文文件
     * @param destFile 输出文件
     * @param angle    rotate
     * @throws Exception 异常
     */
    public static void makeRotate(String srcFile, String destFile, int angle) throws Exception {
        makeRotate(new File(srcFile), new File(destFile), angle);
    }

    /**
     * 图片旋转
     *
     * @param inputStream  输入流
     * @param outputStream 输出流
     * @param angle        rotate
     * @throws Exception 异常
     */
    public static void makeRotate(final InputStream inputStream,
                                  final OutputStream outputStream, int angle) throws Exception {
        BufferedImage dealedImage = null;
        BufferedImage res = null;
        try {
            dealedImage = ImageIO.read(inputStream);
            int width = dealedImage.getWidth();
            int height = dealedImage.getHeight();

            // calculate the new image size
            Rectangle rect_des = CalcRotatedSize(new Rectangle(new Dimension(
                    width, height)), angle);
            res = new BufferedImage(rect_des.width, rect_des.height,
                    BufferedImage.TYPE_INT_RGB);
            Graphics2D g2 = res.createGraphics();
            // transform
            g2.translate((rect_des.width - width) / 2,
                    (rect_des.height - height) / 2);
            g2.rotate(Math.toRadians(angle), width / 2d, height / 2d);

            g2.drawImage(dealedImage, null, null);
            g2.dispose();
            ImageIO.write(res, "png", outputStream);
        } catch (IOException e) {
            log.error("makeRotate error : {}", e);
            throw new Exception(e);
        }
    }

    /**
     * 旋转处理
     */
    public static Rectangle CalcRotatedSize(Rectangle src, int angel) {
        // if angel is greater than 90 degree, we need to do some conversion
        angel = Math.abs(angel);
        if (angel >= 90) {
            if (angel / 90 % 2 == 1) {
                int temp = src.height;
                src.height = src.width;
                src.width = temp;
            }
            angel = angel % 90;
        }

        double r = Math.sqrt((double) src.height * src.height + (double) src.width * src.width) / 2;
        double len = 2 * Math.sin(Math.toRadians(angel) / 2) * r;
        double angel_alpha = (Math.PI - Math.toRadians(angel)) / 2;
        double angel_dalta_width = Math.atan((double) src.height / src.width);
        double angel_dalta_height = Math.atan((double) src.width / src.height);

        int len_dalta_width = (int) (len * Math.cos(Math.PI - angel_alpha
                - angel_dalta_width));
        int len_dalta_height = (int) (len * Math.cos(Math.PI - angel_alpha
                - angel_dalta_height));
        int des_width = src.width + len_dalta_width * 2;
        int des_height = src.height + len_dalta_height * 2;
        return new java.awt.Rectangle(new Dimension(des_width, des_height));
    }
}
