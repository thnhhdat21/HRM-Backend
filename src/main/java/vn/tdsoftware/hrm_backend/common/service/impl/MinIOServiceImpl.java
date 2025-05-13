package vn.tdsoftware.hrm_backend.common.service.impl;

import io.minio.*;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import vn.tdsoftware.hrm_backend.common.exception.BusinessException;
import vn.tdsoftware.hrm_backend.enums.ErrorCode;
import vn.tdsoftware.hrm_backend.common.service.MinIOService;
import vn.tdsoftware.hrm_backend.util.constant.MinIOConstant;

import java.io.InputStream;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class MinIOServiceImpl implements MinIOService {
    private final MinioClient minioClient;

    @Override
    public void uploadFile(String fileName, MultipartFile file) {
        try {
            boolean bucketExists = minioClient.bucketExists(BucketExistsArgs.builder().bucket(MinIOConstant.BUCKET_NAME).build());
            if (!bucketExists) {
                minioClient.makeBucket(MakeBucketArgs.builder().bucket(MinIOConstant.BUCKET_NAME).build());
            }
            minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket(MinIOConstant.BUCKET_NAME)
                            .object(fileName)
                            .contentType(Objects.isNull(file.getContentType()) ? "image/png; image/jpg;" : file.getContentType())
                            .stream(file.getInputStream(), file.getSize(), -1)
                            .build()
            );
        } catch (Exception e) {
            throw new BusinessException(ErrorCode.MINIO_UPLOAD_ERROR);
        }
    }

    @Override
    public InputStreamResource getFile(String pathFile) {
        try {
            boolean bucketExists = minioClient.bucketExists(BucketExistsArgs.builder().bucket(MinIOConstant.BUCKET_NAME).build());
            if (!bucketExists) {
                minioClient.makeBucket(MakeBucketArgs.builder().bucket(MinIOConstant.BUCKET_NAME).build());
            }
            InputStream inputStream = minioClient
                    .getObject(GetObjectArgs.builder().bucket(MinIOConstant.BUCKET_NAME).object(pathFile).build());
            if (Objects.isNull(inputStream)) {
                throw new BusinessException(ErrorCode.MINIO_GET_FILE_ERROR);
            }
            return new InputStreamResource(inputStream);
        } catch (Exception e) {
            throw new BusinessException(ErrorCode.MINIO_GET_FILE_ERROR);
        }
    }
}
