package exception;

public class UniqueValueException extends Exception
{
    @Override
    public String getMessage()
    {
        String superMessage = super.getMessage();
        return superMessage + " Поле должно быть уникальным!";
    }
}
