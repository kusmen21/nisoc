package exception;

public class UnexpectedException extends Exception
{
    @Override
    public String getMessage()
    {
        String superMessage = super.getMessage();
        return superMessage + " Неизвестная ошибка!";
    }
}
