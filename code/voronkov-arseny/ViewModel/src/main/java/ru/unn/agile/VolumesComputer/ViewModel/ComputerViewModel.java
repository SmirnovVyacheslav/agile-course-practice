package ru.unn.agile.VolumesComputer.ViewModel;

import ru.unn.agile.VolumesComputer.Model.*;

public class ComputerViewModel {
    public static final String BAD_VOLUME_STRING
            = "I can't solve volume for this strange figure!";
    public static final String EMPTY_VOLUME_STRING = "";
    public static final String DISABLE_PARAMETER_STRING = "No parameter";
    private String m_parameter1str;
    private String m_parameter2str;
    private String m_parameter3str;
    private double m_parameter1;
    private double m_parameter2;
    private double m_parameter3;
    private FigureName m_figureName;
    private boolean m_inputCorrect;
    private boolean m_parsed;
    private String m_volumeStr;

    public ComputerViewModel() {
        m_parameter1str = m_parameter2str = m_parameter3str = "";
        m_parameter1 = m_parameter2 = m_parameter3 = 0.0;
        m_figureName = FigureName.CUBOID;
        m_inputCorrect = false;
        m_parsed = false;
        m_volumeStr = EMPTY_VOLUME_STRING;
    }
    public void setParameter1(final String parameterString) {
        m_parsed = m_parameter1str.equals(parameterString);
        m_parameter1str = parameterString;
    }
    public void setParameter2(final String parameterString) {
        m_parsed = m_parameter2str.equals(parameterString);
        m_parameter2str = parameterString;
    }
    public void setParameter3(final String parameterString) {
        m_parsed = m_parameter3str.equals(parameterString);
        m_parameter3str = parameterString;
    }
    public void setFigure(final FigureName figureName) {
        m_parsed = m_figureName == figureName;
        m_figureName = figureName;
    }
    public String getParameter1() {
        return m_parameter1str;
    }
    public String getParameter2() {
        return m_parameter2str;
    }
    public String getParameter3() {
        return m_parameter3str;
    }
    public FigureName getFigure() {
        return m_figureName;
    }
    public boolean isParameter1enabled() {
        return m_figureName.getParametersCount() >= 1;
    }
    public boolean isParameter2enabled() {
        return m_figureName.getParametersCount() >= 2;
    }
    public boolean isParameter3enabled() {
        return m_figureName.getParametersCount() >= 3;
    }
    public boolean isInputCorrect() {
        if (!m_parsed) {
            parse();
        }
        return m_inputCorrect;
    }
    public String getVolume() {
        return m_volumeStr;
    }
    public String getParameter1name() {
        return getParameterName(1);
    }
    public String getParameter2name() {
        return getParameterName(2);
    }
    public String getParameter3name() {
        return getParameterName(3);
    }
    public void parse() {
        m_parsed = true;
        final int paramsCount = m_figureName.getParametersCount();
        m_inputCorrect = true;
        if (paramsCount < 1) {
            return;
        }
        if (paramsCount > 3) {
            m_inputCorrect = false;
            return;
        }
        m_parameter1 = parseParameter(m_parameter1str);
        if (m_inputCorrect && paramsCount > 1) {
            m_parameter2 = parseParameter(m_parameter2str);
        }
        if (m_inputCorrect && paramsCount > 2) {
            m_parameter3 = parseParameter(m_parameter3str);
        }
    }
    public void solve() {
        if (!m_parsed) {
            parse();
        }
        if (m_inputCorrect) {
            try {
                if (m_figureName == FigureName.CUBOID) {
                    solveCuboid();
                } else if (m_figureName == FigureName.SPHEROID) {
                    solveSpheroid();
                } else if (m_figureName == FigureName.RIGHT_CYLINDER) {
                    solveRightCylinder();
                } else if (m_figureName == FigureName.RIGHT_CIRCULAR_CONE) {
                    solveRightCircularCone();
                } else {
                    m_volumeStr = BAD_VOLUME_STRING;
                }
            } catch (NegativeParametersException e) {
                m_volumeStr = BAD_VOLUME_STRING;
            }
        } else {
            m_volumeStr = "hehehe";
        }
    }

    private double parseParameter(final String parameterString) {
        double res = 0.0;
        try {
            res = Double.valueOf(parameterString);
        } catch (NumberFormatException e) {
            m_inputCorrect = false;
        }
        return res;
    }
    private String getParameterName(final int parameterIndex) {
        return m_figureName.getParametersCount() >= parameterIndex
                ? m_figureName.getParametersNames()[parameterIndex - 1]
                : DISABLE_PARAMETER_STRING;
    }
    private void solveCuboid() {
        m_volumeStr = String.valueOf(VolumesComputer.solve(new Cuboid(
                m_parameter1, m_parameter2, m_parameter3)));
    }
    private void solveSpheroid() {
        m_volumeStr = String.valueOf(VolumesComputer.solve(
                new Spheroid(m_parameter1, m_parameter2)));
    }
    private void solveRightCylinder() {
        m_volumeStr = String.valueOf(VolumesComputer.solve(new RightCylinder(
                m_parameter1, m_parameter2, m_parameter3)));
    }
    private void solveRightCircularCone() {
        m_volumeStr = String.valueOf(VolumesComputer.solve(
                new RightCircularCone(m_parameter1, m_parameter2)));
    }
}
