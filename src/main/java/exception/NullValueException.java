package exception;

public class NullValueException extends Exception
{
    @Override
    public String getMessage()
    {
        String superMessage = super.getMessage();
        return superMessage + " Все необходимые поля должны быть заполнены!";
    }
}
