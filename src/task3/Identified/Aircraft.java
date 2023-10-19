package task3.Identified;

import task3.Interfaces.HasId;
import task3.IdGenerator;

//Створіть систему управління польотами авіакомпанії. Пропоновані
//класи для ієрархії: літак, аеропорт, пасажир, рейс, розклад польотів,
//квиток.
//Функціональні вимоги:
//1. Створення, видалення, редагування літака
//2. Створення, видалення, редагування рейсу
//3. Створення, видалення, редагування пасажирів
//4. Створення, видалення, редагування айропорта
//5. Відображення розкладів польотів
//6. Створення розкладу польотів
//7. Продаж, скасування квитків
//8. Розрахунок доходів за заданий період часу
public class Aircraft implements HasId {
    private final static IdGenerator idGenerator = new IdGenerator();
    private final Long id;
    private String manufacturer;
    private String model;
    private int economySeat;
    private int firstSeat;
    private int businessSeat;

    public Aircraft(final String manufacturer, final String model, final int economySeat, final int firstSeat, final int businessSeat) {
        this.id = idGenerator.createId();
        this.manufacturer = manufacturer;
        this.model = model;
        this.economySeat = economySeat;
        this.firstSeat = firstSeat;
        this.businessSeat = businessSeat;
    }

    public Long getId() {
        return id;
    }
    public void setManufacturer(final String manufacturer) {
        this.manufacturer = manufacturer;
    }
    public void setModel(final String model) {
        this.model = model;
    }

    public int getEconomySeat() {
        return economySeat;
    }

    public void setEconomySeat(final int economySeat) {
        this.economySeat = economySeat;
    }

    public int getFirstSeat() {
        return firstSeat;
    }

    public void setFirstSeat(final int firstSeat) {
        this.firstSeat = firstSeat;
    }

    public int getBusinessSeat() {
        return businessSeat;
    }

    public void setBusinessSeat(final int businessSeat) {
        this.businessSeat = businessSeat;
    }

    public String toShortString() {
        return manufacturer + " " + model;
    }

    @Override
    public String toString() {
        return "id: " + id + " " + manufacturer + " " + model;
    }
}
