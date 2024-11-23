//Адаптер
// Интерфейс внутренней службы доставки
interface IInternalDeliveryService {
    void deliverOrder(String orderId);
    String getDeliveryStatus(String orderId);
}

// Класс внутренней службы доставки
class InternalDeliveryService implements IInternalDeliveryService {
    @Override
    public void deliverOrder(String orderId) {
        // Вывод сообщения о доставке внутренней службой
        System.out.println("Доставка заказа " + orderId + " внутренней службой.");
    }

    @Override
    public String getDeliveryStatus(String orderId) {
        // Возвращает статус доставки для внутренней службы
        return "Статус внутренней доставки для заказа " + orderId;
    }
}

// Внешняя служба логистики A
class ExternalLogisticsServiceA {
    public void shipItem(int itemId) {
        // Вывод сообщения о доставке внешней службой A
        System.out.println("Доставка товара " + itemId + " через ExternalLogisticsServiceA.");
    }

    public String trackShipment(int shipmentId) {
        // Возвращает информацию о доставке от внешней службы A
        return "Информация об отслеживании для " + shipmentId + " от ExternalLogisticsServiceA.";
    }
}

// Адаптер для ExternalLogisticsServiceA
class LogisticsAdapterA implements IInternalDeliveryService {
    private ExternalLogisticsServiceA externalService;

    public LogisticsAdapterA(ExternalLogisticsServiceA externalService) {
        this.externalService = externalService;
    }

    @Override
    public void deliverOrder(String orderId) {
        // Конвертация orderId в целое число и вызов метода внешней службы
        externalService.shipItem(Integer.parseInt(orderId));
    }

    @Override
    public String getDeliveryStatus(String orderId) {
        // Конвертация orderId в целое число и получение информации о статусе
        return externalService.trackShipment(Integer.parseInt(orderId));
    }
}

// Фабрика для выбора службы доставки
class DeliveryServiceFactory {
    public static IInternalDeliveryService getDeliveryService(String type) {
        if (type.equals("internal")) {
            // Возвращает экземпляр внутренней службы доставки
            return new InternalDeliveryService();
        } else if (type.equals("externalA")) {
            // Возвращает адаптер для внешней службы A
            return new LogisticsAdapterA(new ExternalLogisticsServiceA());
        }
        // Можно добавить другие адаптеры для других внешних служб
        return null;
    }
}

// Клиентский код
public class Main {
    public static void main(String[] args) {
        // Получаем службу доставки через фабрику
        IInternalDeliveryService deliveryService = DeliveryServiceFactory.getDeliveryService("externalA");
        // Доставка заказа
        deliveryService.deliverOrder("123");
        // Получение и вывод статуса доставки
        System.out.println(deliveryService.getDeliveryStatus("123"));
    }
}
