package me.memorytalk.repository.custom;

import com.mysema.query.jpa.JPQLQuery;
import com.mysema.query.types.ConstructorExpression;
import me.memorytalk.domain.Device;
import me.memorytalk.domain.QDevice;
import me.memorytalk.dto.AdminDeviceModel;
import org.springframework.data.jpa.repository.support.QueryDslRepositorySupport;

import java.util.List;

public class DeviceRepositoryImpl extends QueryDslRepositorySupport implements DeviceModelRepository {

    public DeviceRepositoryImpl() {
        super(Device.class);
    }

    public List<AdminDeviceModel> findAdminDeviceModels() {

        QDevice qDevice= QDevice.device;

        JPQLQuery query = from(qDevice);

        List<AdminDeviceModel> adminDeviceModels = query.list(ConstructorExpression.create(AdminDeviceModel.class,
                qDevice.id,
                qDevice.os,
                qDevice.token,
                qDevice.createdDate
        ));

        return adminDeviceModels;
    }
}
