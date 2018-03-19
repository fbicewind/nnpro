package cn.nnnight.util;

import java.io.File;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import cn.nnnight.vo.AvatarVo;
import net.coobird.thumbnailator.Thumbnails;

@Component
public class UploadUtil {

    public static final Logger LOGGER = LoggerFactory.getLogger(UploadUtil.class);

    @Value("${upload.actual.path}")
    private String actualPath;

    @Value("${upload.relative.path}")
    private String relativePath;

    public String uploadTempAvatar(MultipartFile file) {
        String currentDateStr = DateUtil.DateToString(new Date(), "yyyyMMddHHmmssSSS");
        String actualUrl = actualPath + "/img/temp";
        String relativeUrl = relativePath + "/img/temp";
        File outFile = new File(actualUrl);
        if (!outFile.exists()) {
            outFile.mkdirs();
        }
        String fileName = ChineseUtil.filterChinese(file.getOriginalFilename());
        File targetFile = new File(actualUrl + "/" + currentDateStr + "_" + fileName);
        try {
            file.transferTo(targetFile);
            Thumbnails.of(targetFile).size(600, 600).toFile(targetFile);
        } catch (Exception e) {
            LOGGER.error("Failed to upload file. Name: {}, Exception: {}", file.getOriginalFilename(), e);
        }
        return relativeUrl + "/" + currentDateStr + "_" + fileName;
    }

    public String uploadAvatar(AvatarVo vo) {
        String fileUrl = vo.getUrl().replace(relativePath, actualPath);
        String fileName = ChineseUtil.filterChinese(fileUrl.substring(fileUrl.lastIndexOf("/") + 1));
        String lUrl = actualPath + "/img/l";
        String mUrl = actualPath + "/img/m";
        String sUrl = actualPath + "/img/s";
        File outlFile = new File(lUrl);
        if (!outlFile.exists()) {
            outlFile.mkdirs();
        }
        File outmFile = new File(mUrl);
        if (!outmFile.exists()) {
            outmFile.mkdirs();
        }
        File outsFile = new File(sUrl);
        if (!outsFile.exists()) {
            outsFile.mkdirs();
        }
        String lFile = lUrl + "/" + fileName;
        String mFile = mUrl + "/" + fileName;
        String sFile = sUrl + "/" + fileName;
        int left = getProxy(vo.getSelectedAvatar().getLeft(), vo.getBigBox().getLeft(), vo.getBigBox().getWidth(),
                vo.getBigBox().getNaturalWidth());
        int top = getProxy(vo.getSelectedAvatar().getTop(), vo.getBigBox().getTop(), vo.getBigBox().getWidth(),
                vo.getBigBox().getNaturalWidth());
        int width = getWidth(vo.getSelectedAvatar().getWidth(), vo.getBigBox().getWidth(),
                vo.getBigBox().getNaturalWidth());
        try {
            Thumbnails.of(fileUrl).sourceRegion(left, top, width, width).size(250, 250).keepAspectRatio(false)
                    .toFile(lFile);
            Thumbnails.of(fileUrl).sourceRegion(left, top, width, width).size(120, 120).keepAspectRatio(false)
                    .toFile(mFile);
            Thumbnails.of(fileUrl).sourceRegion(left, top, width, width).size(60, 60).keepAspectRatio(false)
                    .toFile(sFile);
        } catch (Exception e) {
            LOGGER.error("Failed to thumbnail image. Temp Url: {}, Exception: {}", fileUrl, e);
        }
        return fileName;
    }

    public String uploadBlogImage(MultipartFile file) {
        String currentDateStr = DateUtil.DateToString(new Date(), "yyyyMMddHHmmssSSS");
        String currentDay = DateUtil.DateToString(new Date(), "yyyyMMdd");
        String actualLargeUrl = actualPath + "/img/blog/l/" + currentDay;
        String actualSmallUrl = actualPath + "/img/blog/s/" + currentDay;
        String relativeLargeUrl = relativePath + "/img/blog/l/" + currentDay;
        File outLargeFile = new File(actualLargeUrl);
        File outSmallFile = new File(actualSmallUrl);
        if (!outLargeFile.exists()) {
            outLargeFile.mkdirs();
        }
        if (!outSmallFile.exists()) {
            outSmallFile.mkdirs();
        }
        String fileName = ChineseUtil.filterChinese(file.getOriginalFilename());
        File targetLargeFile = new File(actualLargeUrl + "/" + currentDateStr + "_" + fileName);
        File targetSmallFile = new File(actualSmallUrl + "/" + currentDateStr + "_" + fileName);
        try {
            file.transferTo(targetLargeFile);
            Thumbnails.of(targetLargeFile).size(200, 120).toFile(targetSmallFile);
        } catch (Exception e) {
            LOGGER.error("Failed to upload file. Name: {}, Exception: {}", file.getOriginalFilename(), e);
        }
        return relativeLargeUrl + "/" + currentDateStr + "_" + fileName;
    }

    private int getProxy(double selected, double box, double boxWidth, double naturalWidth) {
        double proxy = (naturalWidth / boxWidth) * (selected - box);
        return (new Double(proxy)).intValue();
    }

    private int getWidth(double selectedWidth, double boxWidth, double naturalWidth) {
        double width = (naturalWidth / boxWidth) * selectedWidth;
        return (new Double(width)).intValue();
    }

    public String uploadPhotos(MultipartFile file) {
        String returnName = "";
        String currentDateStr = DateUtil.DateToString(new Date(), "yyyyMMddHHmmssSSS");
        String currentDay = DateUtil.DateToString(new Date(), "yyyyMMdd");
        String actualLargeUrl = actualPath + "/album/l/" + currentDay;
        String actualSmallUrl = actualPath + "/album/s/" + currentDay;
        File outLargeFile = new File(actualLargeUrl);
        File outSmallFile = new File(actualSmallUrl);
        if (!outLargeFile.exists()) {
            outLargeFile.mkdirs();
        }
        if (!outSmallFile.exists()) {
            outSmallFile.mkdirs();
        }
        String fileName = ChineseUtil.filterChinese(file.getOriginalFilename());
        File targetLargeFile = new File(actualLargeUrl + "/" + currentDateStr + "_" + fileName);
        File targetSmallFile = new File(actualSmallUrl + "/" + currentDateStr + "_" + fileName);
        try {
            file.transferTo(targetLargeFile);
            Thumbnails.of(targetLargeFile).size(200, 160).toFile(targetSmallFile);
            returnName = currentDay + "/" + currentDateStr + "_" + fileName;
        } catch (Exception e) {
            LOGGER.error("Failed to upload file. Name: {}, Exception: {}", file.getOriginalFilename(), e);
        }
        return returnName;
    }

    public String uploadMusicFile(MultipartFile file) {
        String currentDateStr = DateUtil.DateToString(new Date(), "yyyyMMddHHmmssSSS");
        String actualUrl = actualPath + "/music/file";
        String relativeUrl = relativePath + "/music/file";
        File outFile = new File(actualUrl);
        if (!outFile.exists()) {
            outFile.mkdirs();
        }
        String fileName = ChineseUtil.filterChinese(file.getOriginalFilename());
        File targetFile = new File(actualUrl + "/" + currentDateStr + "_" + fileName);
        try {
            file.transferTo(targetFile);
        } catch (Exception e) {
            LOGGER.error("Failed to upload file. Name: {}, Exception: {}", file.getOriginalFilename(), e);
        }
        return relativeUrl + "/" + currentDateStr + "_" + fileName;
    }

    public String uploadMusicCover(MultipartFile file) {
        String currentDateStr = DateUtil.DateToString(new Date(), "yyyyMMddHHmmssSSS");
        String actualUrl = actualPath + "/music/cover";
        String relativeUrl = relativePath + "/music/cover";
        File outFile = new File(actualUrl);
        if (!outFile.exists()) {
            outFile.mkdirs();
        }
        String fileName = ChineseUtil.filterChinese(file.getOriginalFilename());
        File targetFile = new File(actualUrl + "/" + currentDateStr + "_" + fileName);
        try {
            file.transferTo(targetFile);
            Thumbnails.of(targetFile).size(220, 220).toFile(targetFile);
        } catch (Exception e) {
            LOGGER.error("Failed to upload file. Name: {}, Exception: {}", file.getOriginalFilename(), e);
        }
        return relativeUrl + "/" + currentDateStr + "_" + fileName;
    }
}
