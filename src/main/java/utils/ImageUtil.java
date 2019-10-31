package utils;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.font.TextAttribute;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;
import javax.imageio.ImageIO;
import sun.font.FontDesignMetrics;

public class ImageUtil {

    /**
     * @param fileUrl 文件网络路径
     * @return 读取到的缓存图像
     * @throws IOException 路径错误或者不存在该文件时抛出IO异常
     */
    public static BufferedImage getBufferedImage(String fileUrl) {
        BufferedImage bufferedImage = null;
        // 构造URL
        URL url = null;
        InputStream is = null;
        try {
            url = new URL(fileUrl);

            // 打开连接
            URLConnection con = url.openConnection();
            // 输入流
            is = con.getInputStream();
            bufferedImage = ImageIO.read(is);

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (is != null) {
                // 完毕，关闭所有链接
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return bufferedImage;
    }

    /**
     * @param fileUrl 文件绝对路径或相对路径
     * @return 读取到的缓存图像
     * @throws IOException 路径错误或者不存在该文件时抛出IO异常
     */
    public static BufferedImage getBufferedImageLocal(String fileUrl) throws IOException {
        return ImageIO.read(new File(fileUrl));
    }


    /**
     * 待合并的两张图必须满足这样的前提，如果水平方向合并，则高度必须相等；如果是垂直方向合并，宽度必须相等。 mergeImage方法不做判断，自己判断。
     *
     * @param img1 待合并的第一张图
     * @param img2 带合并的第二张图
     * @return 返回合并后的BufferedImage对象
     * @throws IOException
     */
    public static BufferedImage mergeImage(BufferedImage img1, BufferedImage img2)
        throws IOException {
        int w1 = img1.getWidth();
        int h1 = img1.getHeight();
        int w2 = img2.getWidth();
        int h2 = img2.getHeight();

        // 从图片中读取RGB
        int[] ImageArrayOne = new int[w1 * h1];
        ImageArrayOne = img1.getRGB(0, 0, w1, h1, ImageArrayOne, 0, w1); // 逐行扫描图像中各个像素的RGB到数组中
        int[] ImageArrayTwo = new int[w2 * h2];
        ImageArrayTwo = img2.getRGB(0, 0, w2, h2, ImageArrayTwo, 0, w2);

        // 生成新图片
        BufferedImage DestImage = null;
        DestImage = new BufferedImage(w1, h1 + h2, BufferedImage.TYPE_INT_RGB);
        DestImage.setRGB(0, 0, w1, h1, ImageArrayOne, 0, w1); // 设置上半部分或左半部分的RGB
        DestImage.setRGB(0, h1, w2, h2, ImageArrayTwo, 0, w2); // 设置下半部分的RGB
        return DestImage;
    }

    /**
     * BufferedImage 转 InputStream
     * <p>Title: getImageStream</p>
     * <p>Description: 获取图片InputStream</p>
     *
     * @param destImg
     * @return
     */
    public static InputStream getImageStream(BufferedImage destImg) {
        InputStream is = null;

        ByteArrayOutputStream os = new ByteArrayOutputStream();
        BufferedOutputStream bos = new BufferedOutputStream(os);
        try {
            ImageIO.write(destImg, "png", bos);
        } catch (IOException e) {
            e.printStackTrace();
        }
        is = new BufferedInputStream(new ByteArrayInputStream(os.toByteArray()));

        return is;
    }

    /**
     * <p>Title: drawTextInImg</p>
     * <p>Description: 图片上添加文字业务需求要在图片上添加水</p>
     *
     * @param text
     */
    public static BufferedImage drawTextInImg(String text, int srcImgWidth, Font font) {
        int fontSize = font.getSize();
        int fontlen = getWatermarkLength(text, font);
        int line = fontlen / srcImgWidth;//文字长度相对于图片宽度应该有多少行
        int srcImgHeight = (line + 2) * fontSize;
        ;

        BufferedImage bimage = new BufferedImage(srcImgWidth, srcImgHeight,
            BufferedImage.TYPE_INT_RGB);

        Graphics2D g = bimage.createGraphics();
        g.setColor(Color.white);
        g.fillRect(0, 0, srcImgWidth, srcImgHeight);

        drawText(g, text, srcImgWidth, font, 0, 0, 0, null, null);
        g.dispose();
        return bimage;
    }

    /**
     * <p>Title: drawTextInImg</p>
     * <p>Description: 图片上添加文字业务需求要在图片上添加水</p>
     *
     * @param
     */
    public static BufferedImage drawTextInImg(Map<String, String> map) {
        int srcImgWidth = 1500;
        int fontSize = 88;
        int rowSpacing = 50;
        Map<TextAttribute, Object> attributes = new HashMap<>();

        attributes.put(TextAttribute.FAMILY, "Microsoft YaHei");
        attributes.put(TextAttribute.WEIGHT, TextAttribute.WEIGHT_MEDIUM);
        attributes.put(TextAttribute.SIZE, fontSize);
        Font font = Font.getFont(attributes);

        attributes.put(TextAttribute.FAMILY, "Microsoft YaHei");
        attributes.put(TextAttribute.WEIGHT, TextAttribute.WEIGHT_MEDIUM);
        attributes.put(TextAttribute.SIZE, 66);
        Font keyFont = Font.getFont(attributes);
        /*int line = 0;
        for (String key: map.keySet() ) {
            String text = key + Strings.nullToEmpty(map.get(key));
            int fontlen = getWatermarkLength(text, font);
            if(fontlen < srcImgWidth){
                line += 1;
            }else{
                line += fontlen / srcImgWidth;//文字长度相对于图片宽度应该有多少行
            }
        }


        int srcImgHeight = (line + 2) * (fontSize+rowSpacing);*/
        int srcImgHeight = 1100;

        BufferedImage bimage = new BufferedImage(srcImgWidth, srcImgHeight,
            BufferedImage.TYPE_INT_RGB);

        Graphics2D g = bimage.createGraphics();
        g.setColor(Color.white);
        g.fillRect(0, 0, srcImgWidth, srcImgHeight);
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int beginY = 0;
        int beginX = 90 + 40;
        int valueBeginX = beginX + fontSize * 5;
        int keyMaxWidth = 100 * 4;
        int valueMaxWidth = srcImgWidth - keyMaxWidth - 200;
        for (String key : map.keySet()) {
            beginY += (fontSize + rowSpacing);
            drawText(g, key, keyMaxWidth, keyFont, beginX, beginY + 20, rowSpacing,
                new Color(255, 117, 0), Color.white);
            drawText(g, map.get(key), valueMaxWidth, font, valueBeginX, beginY, rowSpacing, null,
                null);
        }

        g.dispose();
        return bimage;
    }

    /**
     * <p>Title: drawTextInImg</p>
     * <p>Description: 图片上添加文字业务需求要在图片上添加水</p>
     */
    public static BufferedImage drawImgInImg(BufferedImage targetImg, BufferedImage img, int beginX,
        int beginY, int zoom) {

        Graphics2D g = targetImg.createGraphics();

        g.drawImage(img, beginX, beginY, img.getWidth() / zoom, img.getHeight() / zoom, null);

        g.dispose();
        return targetImg;
    }

    static void drawText(Graphics2D g, String text, int srcImgWidth, Font font, int beginX,
        int beginY, int rowSpacing, Color backGroundColor, Color fontColor) {
        if (backGroundColor != null) {
            g.setColor(backGroundColor);
            g.fillRect(beginX - 40, beginY - 5, font.getSize() * 4 + 50 * 2,
                font.getSize() + 15 * 2);
        }
        if (fontColor != null) {
            g.setColor(fontColor);
        } else {
            g.setColor(Color.black);
        }
        g.setFont(font);

        int fontSize = font.getSize();

        //文字叠加,自动换行叠加
        int tempX = beginX + 2;
        int tempY = beginY + fontSize;
        int tempCharLen = 0;//单字符长度
        int tempLineLen = 0;//单行字符总长度临时计算
        StringBuffer sb = new StringBuffer();
        int textMaxWidth = srcImgWidth - 10;
        for (int i = 0; i < text.length(); i++) {
            char tempChar = text.charAt(i);
            tempCharLen = getCharLen(tempChar, g);

            tempLineLen += tempCharLen;

            if (tempLineLen >= textMaxWidth) {
                //长度已经满一行,进行文字叠加
                g.drawString(sb.toString(), tempX, tempY);

                sb.delete(0, sb.length());//清空内容,重新追加

                tempY += (fontSize + rowSpacing);

                tempLineLen = 0;
            }
            sb.append(tempChar);//追加字符
        }

        g.drawString(sb.toString(), tempX, tempY);//最后叠加余下的文字
    }

    /**
     * 获取水印文字总长度
     *
     * @paramwaterMarkContent水印的文字
     * @paramg
     * @return水印文字总长度
     */
    public static int getWatermarkLength(String waterMarkContent, Font font) {
        return FontDesignMetrics.getMetrics(font)
            .charsWidth(waterMarkContent.toCharArray(), 0, waterMarkContent.length());
    }

    public static int getCharLen(char c, Graphics2D g) {
        return g.getFontMetrics(g.getFont()).charWidth(c);
    }

    public static BufferedImage test() {
        BufferedImage bufferedImage = null;
        try {
            String text = "java项目开发难免会遇到不同的客户的需求，没错我们要不断的满足客户的需求。今天在项目遇到一个将两张图片纵向拼接起来成为一张图片";
            text =
                "文学是一种语言艺术，是话语蕴藉中的审美意识形态。诗歌、散文、小说、剧本、寓言、童话等不同体裁，是文学的重要表现形式。文学以不同的形式即体裁，表现内心情感，再现一定时期和一定地域的社会生活。作为学科门类理解的文学，包括中国语言文学、外国语言文学及新闻传播学。\n"
                    + "文学是属于人文学科的学科分类之一，与哲学、宗教、法律、政治并驾于社会建筑上层。它起源于人类的思维活动。最先出现的是口头文学，一般是与音乐联结为可以演唱的抒情诗歌。最早形成书面文学的有中国的《诗经》、印度的《罗摩衍那》和古希腊的《伊利昂纪》等。中国先秦时期将以文字写成的作品都统称为文学，魏晋以后才逐渐将文学作品单独列出。欧洲传统文学理论分类法将文学分为诗、散文、戏剧三大类。现代通常将文学分为诗歌、小说、散文、戏剧四大类别。\n"
                    + "文学是语言文字的艺术，是社会文化的一种重要表现形式，是对美的体现。文学作品是作家用独特的语言艺术表现其独特的心灵世界的作品，离开了这样两个极具个性特点的独特性就没有真正的文学作品。一个杰出的文学家就是一个民族心灵世界的英雄。文学代表一个民族的艺术和智慧。文学，是一种将语言文字用于表达社会生活和心理活动的学科，属社会意识形态范畴。";
            bufferedImage = getBufferedImage("D:\\temp\\loading.jpg");
            Map<TextAttribute, Object> attributes = new HashMap<>();

            attributes.put(TextAttribute.FAMILY, "Microsoft YaHei");
            attributes.put(TextAttribute.WEIGHT, TextAttribute.WEIGHT_MEDIUM);
            attributes.put(TextAttribute.SIZE, 14);
            Font font = Font.getFont(attributes);
            BufferedImage bufferedImage1 = drawTextInImg(text, 430, font);
            bufferedImage = mergeImage(bufferedImage1, bufferedImage);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bufferedImage;
    }


    public static void main(String[] args) {

    }

}
