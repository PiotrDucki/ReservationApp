package bd;

public class Ticket
{
	private int rowNumber;
	private int seatNumber;
	private int discount;

	public int getRowNumber()
	{
		return rowNumber;
	}

	public void setRowNumber(int rowNumber)
	{
		this.rowNumber = rowNumber;
	}

	public int getSeatNumber()
	{
		return seatNumber;
	}

	public void setSeatNumber(int seatNumber)
	{
		this.seatNumber = seatNumber;
	}

	public int getDiscount()
	{
		return discount;
	}

	public void setDiscount(int discount)
	{
		this.discount = discount;
	}

	public Ticket(int rowNumber, int seatNumber, int discount)
	{
		super();
		this.rowNumber = rowNumber;
		this.seatNumber = seatNumber;
		this.discount = discount;
	}

	@Override
	public String toString()
	{
		return "Ticket [rowNumber=" + rowNumber + ", seatNumber=" + seatNumber + ", discount=" + discount + "]";
	}

}
